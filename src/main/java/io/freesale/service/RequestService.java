package io.freesale.service;

import io.freesale.dto.MakeOfferDto;
import io.freesale.dto.MakeRequestDto;
import io.freesale.dto.RequestDto;
import io.freesale.exception.IllegalActionException;
import io.freesale.exception.OfferNotFoundException;
import io.freesale.exception.RequestNotFoundException;
import io.freesale.model.Offer;
import io.freesale.model.Offer.Status;
import io.freesale.model.Request;
import io.freesale.model.Transaction;
import io.freesale.repository.OfferRepository;
import io.freesale.repository.RequestRepository;
import io.freesale.repository.TransactionRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestService {

  private final RequestRepository requestRepository;
  private final OfferRepository offerRepository;
  private final TransactionRepository transactionRepository;
  private final UploadService uploadService;

  public Mono<RequestDto> makeRequest(Mono<MakeRequestDto> makeRequestDto, String userId) {
    return makeRequestDto
        .map(dto -> Request.of(dto.getTitle(), userId))
        .flatMap(requestRepository::save)
        .map(request -> RequestDto
            .builder()
            .id(request.getId())
            .title(request.getTitle())
            .offers(Collections.emptyList())
            .userId(request.getUserId())
            .build());
  }

  public Mono<RequestDto> makeOffer(String requestId, Mono<MakeOfferDto> makeOfferDto,
      String userId) {
    return requestRepository
        .findById(requestId)
        .switchIfEmpty(Mono.error(RequestNotFoundException::new))
        .handle((request, sink) -> {
          if (request.getUserId().equals(userId)) {
            sink.error(new IllegalActionException("You cannot fulfil your own request"));
          } else {
            sink.next(request);
          }
        })
        .cast(Request.class)
        .flatMap(request -> makeOfferDto
            .map(dto -> Offer.of(dto.getAmount(), Status.PENDING, requestId, userId))
            .flatMap(offerRepository::save)
            .flatMapMany(offer -> offerRepository.findByRequestId(requestId))
            .collectList()
            .map(offers -> RequestDto
                .builder()
                .id(requestId)
                .title(request.getTitle())
                .offers(offers)
                .userId(request.getUserId())
                .build()));
  }

  public Mono<RequestDto> uploadOfferImages(String requestId, String offerId, Flux<Part> parts,
      String userId) {
    return offerRepository
        .findById(offerId)
        .switchIfEmpty(Mono.error(OfferNotFoundException::new))
        .handle((offer, sink) -> {
          if (!offer.getUserId().equals(userId)) {
            sink.error(new IllegalActionException("This offer belongs to another user"));
          } else {
            sink.next(offer);
          }
        })
        .cast(Offer.class)
        .flatMap(offer -> uploadService
            .upload(parts)
            .collectList()
            .map(keys -> {
              offer.getImages().addAll(keys);
              return offer;
            })
            .flatMap(offerRepository::save))
        .flatMapMany(offer -> offerRepository.findByRequestId(requestId))
        .collectList()
        .flatMap(offers -> requestRepository
            .findById(requestId)
            .map(request -> RequestDto
                .builder()
                .id(requestId)
                .title(request.getTitle())
                .offers(offers)
                .userId(request.getUserId())
                .build()));
  }

  public Mono<RequestDto> handleOffer(String requestId, String offerId, boolean accept,
      String userId) {
    return requestRepository
        .findById(requestId)
        .switchIfEmpty(Mono.error(RequestNotFoundException::new))
        .handle((request, sink) -> {
          if (!request.getUserId().equals(userId)) {
            sink.error(new IllegalActionException("This request belongs to another user"));
          } else {
            sink.next(request);
          }
        })
        .cast(Request.class)
        .flatMap(request -> offerRepository
            .findByIdAndRequestId(offerId, requestId)
            .switchIfEmpty(Mono.error(OfferNotFoundException::new))
            .map(offer -> offer.withStatus(accept ? Status.ACCEPTED : Status.DECLINED))
            .flatMap(offerRepository::save)
            .flatMapMany(offer -> offerRepository.findByRequestId(requestId))
            .collectList()
            .map(offers -> RequestDto
                .builder()
                .id(requestId)
                .title(request.getTitle())
                .offers(offers)
                .userId(userId)
                .build()))
        .doOnSuccess(requestDto -> {
          if (accept) {
            transactionRepository
                .save(Transaction.of(requestId, offerId))
                .subscribe();
          }
        });
  }

  public Flux<RequestDto> getRequests() {
    return requestRepository
        .findAll()
        .flatMap(request -> offerRepository
            .findByRequestId(request.getId())
            .collectList()
            .map(offers -> RequestDto
                .builder()
                .id(request.getId())
                .title(request.getTitle())
                .offers(offers)
                .userId(request.getUserId())
                .build()));
  }

}
