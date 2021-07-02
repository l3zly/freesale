package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class MakeRequestDto {

  @JsonProperty("title")
  String title;

}
