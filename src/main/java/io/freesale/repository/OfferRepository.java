package io.freesale.repository;

import io.freesale.model.Offer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {

  Flux<Offer> findByRequestId(String requestId);

  Mono<Offer> findByIdAndRequestId(String id, String requestId);

}
