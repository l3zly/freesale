package io.freesale.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
public class Request {

  private final String id;
  private final String title;
  private final String userId;

  public Request(String id, String title, String userId) {
    this.id = id;
    this.title = title;
    this.userId = userId;
  }

  public static Request of(String title, String userId) {
    return new Request(null, title, userId);
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getUserId() {
    return userId;
  }

}
