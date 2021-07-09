package io.freesale.service;

import io.freesale.dto.RequestDto;
import io.freesale.model.Offer.Status;
import io.freesale.repository.OfferRepository;
import io.freesale.repository.RequestRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BalanceService {

  private final RequestRepository requestRepository;
  private final OfferRepository offerRepository;

  public Mono<String> calculateBalance(String userId) {
    return requestRepository
        .findAll()
        .flatMap(request -> offerRepository
            .findByRequestId(request.getId())
            .collectList()
            .map(offers -> RequestDto
                .builder()
                .id(request.getId())
                .title(request.getTitle())
                .offers(offers)
                .userId(request.getUserId())
                .build()))
        .reduce(BigDecimal.ZERO, (balance, requestDto) -> {
          if (requestDto.getUserId().equals(userId)) {
            return requestDto
                .getOffers()
                .stream()
                .filter(offer -> offer.getStatus() == Status.ACCEPTED)
                .findAny()
                .map(offer -> balance
                    .subtract(BigDecimal.valueOf(Double.parseDouble(offer.getAmount()))))
                .orElse(balance);
          }
          return requestDto
              .getOffers()
              .stream()
              .filter(offer -> offer.getUserId().equals(userId))
              .filter(offer -> offer.getStatus() == Status.ACCEPTED)
              .findAny()
              .map(offer -> balance.add(BigDecimal.valueOf(Double.parseDouble(offer.getAmount()))))
              .orElse(balance);
        })
        .map(BigDecimal::toPlainString);
  }

}
