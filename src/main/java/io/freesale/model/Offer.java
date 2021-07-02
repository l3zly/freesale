package io.freesale.model;

import java.util.Collections;
import java.util.List;
import lombok.Value;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offers")
@Value
public class Offer {

  String id;
  String amount;
  @With
  Status status;
  List<String> images;
  String requestId;
  String userId;

  public static Offer of(String amount, Status status, String requestId, String userId) {
    return new Offer(null, amount, status, Collections.emptyList(), requestId, userId);
  }

  public enum Status {
    ACCEPTED,
    DECLINED,
    PENDING
  }

}
