package io.freesale.controller;

import io.freesale.dto.MakeOfferDto;
import io.freesale.model.Offer;
import io.freesale.security.SecurityUser;
import io.freesale.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Offer> makeOffer(@RequestBody Mono<MakeOfferDto> makeOfferDto, Authentication authentication) {
        return offerService.makeOffer(makeOfferDto, ((SecurityUser) authentication.getPrincipal()).getUser().getId());
    }

}
