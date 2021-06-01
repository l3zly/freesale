package io.freesale.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  private final String id;
  private final String phone;
  private final String password;

  public User(String id, String phone, String password) {
    this.id = id;
    this.phone = phone;
    this.password = password;
  }

  public static User of(String phone, String password) {
    return new User(null, phone, password);
  }

  public String getId() {
    return id;
  }

  public String getPhone() {
    return phone;
  }

  public String getPassword() {
    return password;
  }

}
