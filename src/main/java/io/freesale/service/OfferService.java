package io.freesale.service;

import io.freesale.dto.MakeOfferDto;
import io.freesale.model.Offer;
import io.freesale.repository.OfferRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OfferService {

  private final OfferRepository offerRepository;

  public OfferService(OfferRepository offerRepository) {
    this.offerRepository = offerRepository;
  }

  public Mono<Offer> makeOffer(Mono<MakeOfferDto> makeOfferDto, String userId) {
    return makeOfferDto
        .map(dto -> Offer.of(dto.getAmount(), Offer.Status.PENDING, null, userId))
        .flatMap(offerRepository::save);
  }

  public Flux<Offer> findOpenOffers() {
    return offerRepository.findByRequestIdIsNull();
  }

}
