package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TokenDto {

  @JsonProperty("accessToken")
  String accessToken;
  @JsonProperty("tokenType")
  String tokenType;

}
