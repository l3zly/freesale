package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserDto {

  @JsonProperty("id")
  String id;
  @JsonProperty("phone")
  String phone;

}
