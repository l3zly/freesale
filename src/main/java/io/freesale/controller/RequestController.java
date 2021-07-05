package io.freesale.controller;

import io.freesale.dto.ErrorDto;
import io.freesale.dto.MakeOfferDto;
import io.freesale.dto.MakeRequestDto;
import io.freesale.dto.RequestDto;
import io.freesale.exception.IllegalActionException;
import io.freesale.exception.OfferNotFoundException;
import io.freesale.exception.RequestNotFoundException;
import io.freesale.security.SecurityUser;
import io.freesale.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

  private final RequestService requestService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<RequestDto> makeRequest(@RequestBody Mono<MakeRequestDto> makeRequestDto,
      Authentication authentication) {
    return requestService.makeRequest(makeRequestDto,
        ((SecurityUser) authentication.getPrincipal()).getUser().getId());
  }

  @PostMapping("/{requestId}/offers")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<RequestDto> makeOffer(@PathVariable String requestId,
      @RequestBody Mono<MakeOfferDto> makeOfferDto, Authentication authentication) {
    return requestService.makeOffer(requestId, makeOfferDto,
        ((SecurityUser) authentication.getPrincipal()).getUser().getId());
  }

  @PutMapping("/{requestId}/offers/{offerId}/accept")
  public Mono<RequestDto> acceptOffer(@PathVariable String requestId, @PathVariable String offerId,
      Authentication authentication) {
    return requestService.handleOffer(requestId, offerId, true,
        ((SecurityUser) authentication.getPrincipal()).getUser().getId());
  }

  @PutMapping("/{requestId}/offers/{offerId}/decline")
  public Mono<RequestDto> declineOffer(@PathVariable String requestId, @PathVariable String offerId,
      Authentication authentication) {
    return requestService.handleOffer(requestId, offerId, false,
        ((SecurityUser) authentication.getPrincipal()).getUser().getId());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({RequestNotFoundException.class, OfferNotFoundException.class})
  public ErrorDto handleNotFound(Exception e) {
    return new ErrorDto(e.getMessage());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(IllegalActionException.class)
  public ErrorDto handleIllegalAction(Exception e) {
    return new ErrorDto(e.getMessage());
  }

}
