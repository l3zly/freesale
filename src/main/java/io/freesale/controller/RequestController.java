package io.freesale.controller;

import io.freesale.dto.MakeRequestDto;
import io.freesale.model.Request;
import io.freesale.security.SecurityUser;
import io.freesale.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/requests")
public class RequestController {

  private final RequestService requestService;

  public RequestController(RequestService requestService) {
    this.requestService = requestService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Request> makeRequest(@RequestBody Mono<MakeRequestDto> makeRequestDto,
      Authentication authentication) {
    return requestService.makeRequest(makeRequestDto,
        ((SecurityUser) authentication.getPrincipal()).getUser().getId());
  }

}
