package io.freesale.model;

import lombok.Value;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Value
public class User {

  String id;
  @With
  String phone;
  @With
  String password;

  public static User of(String phone, String password) {
    return new User(null, phone, password);
  }

}
