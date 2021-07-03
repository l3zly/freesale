package io.freesale.repository;

import io.freesale.model.Offer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {

  Flux<Offer> findByRequestId(String requestId);

}
