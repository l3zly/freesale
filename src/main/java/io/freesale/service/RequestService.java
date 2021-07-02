package io.freesale.service;

import io.freesale.dto.MakeRequestDto;
import io.freesale.model.Request;
import io.freesale.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestService {

  private final RequestRepository requestRepository;

  public Mono<Request> makeRequest(Mono<MakeRequestDto> makeRequestDto, String userId) {
    return makeRequestDto
        .map(dto -> Request.of(dto.getTitle(), userId))
        .flatMap(requestRepository::save);
  }

}
