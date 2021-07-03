package io.freesale.service;

import io.freesale.dto.MakeOfferDto;
import io.freesale.dto.MakeRequestDto;
import io.freesale.dto.RequestDto;
import io.freesale.exception.IllegalActionException;
import io.freesale.exception.RequestNotFoundException;
import io.freesale.model.Offer;
import io.freesale.model.Offer.Status;
import io.freesale.model.Request;
import io.freesale.repository.OfferRepository;
import io.freesale.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestService {

  private final RequestRepository requestRepository;
  private final OfferRepository offerRepository;

  public Mono<Request> makeRequest(Mono<MakeRequestDto> makeRequestDto, String userId) {
    return makeRequestDto
        .map(dto -> Request.of(dto.getTitle(), userId))
        .flatMap(requestRepository::save);
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

}
