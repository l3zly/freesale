package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MakeRequestDto {

  private final String title;

  public MakeRequestDto(@JsonProperty("title") String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

}
