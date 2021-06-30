package io.freesale.repository;

import io.freesale.model.Request;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RequestRepository extends ReactiveMongoRepository<Request, String> {

}
