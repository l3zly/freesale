package io.freesale.model;

import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
@Value
public class Request {

  String id;
  String title;
  String userId;

  public static Request of(String title, String userId) {
    return new Request(null, title, userId);
  }

}
