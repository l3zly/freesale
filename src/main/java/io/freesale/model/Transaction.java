package io.freesale.model;

import java.time.LocalDateTime;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
@Value
public class Transaction {

  String id;
  String requestId;
  String offerId;
  boolean received;
  LocalDateTime initialised;

  public static Transaction of(String requestId, String offerId) {
    return new Transaction(null, requestId, offerId, false, LocalDateTime.now());
  }

}
