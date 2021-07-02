package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CreateUserDto {

  @JsonProperty("phone")
  String phone;
  @JsonProperty("password")
  String password;

}
