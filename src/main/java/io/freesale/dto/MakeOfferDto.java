package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class MakeOfferDto {

  @JsonProperty("amount")
  String amount;

}
