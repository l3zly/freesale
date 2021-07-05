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
import io.freesale.repository.OfferRepository;
import io.freesale.repository.RequestRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestService {

  private final RequestRepository requestRepository;
  private final OfferRepository offerRepository;

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
                .build()));
  }

}
