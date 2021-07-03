package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.freesale.model.Offer;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequestDto {

  @JsonProperty("id")
  String id;
  @JsonProperty("title")
  String title;
  @JsonProperty("offers")
  List<Offer> offers;
  @JsonProperty("userId")
  String userId;

}
