package io.freesale.service;

import io.freesale.dto.MakeRequestDto;
import io.freesale.model.Request;
import io.freesale.repository.RequestRepository;
import java.util.Collections;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RequestService {

  private final RequestRepository repository;

  public RequestService(RequestRepository repository) {
    this.repository = repository;
  }

  public Mono<Request> makeRequest(Mono<MakeRequestDto> makeRequestDto, String userId) {
    return makeRequestDto
        .map(dto -> Request.of(dto.getTitle(), Collections.emptyList(), userId))
        .flatMap(repository::save);
  }

}
