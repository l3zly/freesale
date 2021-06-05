package io.freesale.service;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;

@Service
public class UploadService {

  private static final String BUCKET = "freesale";
  private static final int MIN_PART_SIZE = 5 * 1024 * 1024;

  private final S3AsyncClient s3;

  public UploadService(S3AsyncClient s3) {
    this.s3 = s3;
  }

  public Flux<String> upload(Flux<Part> parts) {
    return parts.ofType(FilePart.class).flatMap(this::save);
  }

  private Mono<String> save(FilePart part) {
    var key = UUID.randomUUID().toString();
    var state = new State(key);
    return Mono
        .fromFuture(s3.createMultipartUpload(CreateMultipartUploadRequest
            .builder()
            .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .key(key)
            .bucket(BUCKET)
            .build()))
        .flatMapMany(response -> {
          state.uploadId = response.uploadId();
          return part.content();
        })
        .bufferUntil(buffer -> {
          state.buffered += buffer.readableByteCount();
          if (state.buffered < MIN_PART_SIZE) {
            return false;
          }
          state.buffered = 0;
          return true;
        })
        .map(this::concat)
        .flatMap(buffer -> upload(state, buffer))
        .onBackpressureBuffer()
        .reduce(state, (accuState, completedPart) -> {
          accuState.completedParts.put(completedPart.partNumber(), completedPart);
          return accuState;
        })
        .flatMap(this::complete)
        .map(response -> state.key);
  }

  private ByteBuffer concat(List<DataBuffer> buffers) {
    var data = ByteBuffer
        .allocate(buffers.stream().mapToInt(DataBuffer::readableByteCount).sum());
    buffers.forEach(buffer -> data.put(buffer.asByteBuffer()));
    return data.rewind();
  }

  private Mono<CompletedPart> upload(State state, ByteBuffer buffer) {
    var partNumber = ++state.parts;
    return Mono
        .fromFuture(s3.uploadPart(UploadPartRequest
                .builder()
                .bucket(BUCKET)
                .key(state.key)
                .partNumber(partNumber)
                .uploadId(state.uploadId)
                .contentLength((long) buffer.capacity())
                .build(),
            AsyncRequestBody.fromPublisher(Mono.just(buffer))))
        .map(response -> CompletedPart
            .builder()
            .eTag(response.eTag())
            .partNumber(partNumber)
            .build());
  }

  private Mono<CompleteMultipartUploadResponse> complete(State state) {
    return Mono
        .fromFuture(s3.completeMultipartUpload(CompleteMultipartUploadRequest
            .builder()
            .bucket(BUCKET)
            .uploadId(state.uploadId)
            .multipartUpload(CompletedMultipartUpload
                .builder()
                .parts(state.completedParts.values())
                .build())
            .key(state.key)
            .build()));
  }

  private static class State {

    String key;
    String uploadId;
    int parts;
    int buffered;
    Map<Integer, CompletedPart> completedParts = new HashMap<>();

    public State(String key) {
      this.key = key;
    }

  }

}
