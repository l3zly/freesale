package io.freesale.repository;

import io.freesale.model.Offer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {

}
