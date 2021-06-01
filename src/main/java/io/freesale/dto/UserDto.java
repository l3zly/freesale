package io.freesale.dto;

public class UserDto {

  private final String id;
  private final String phone;

  public UserDto(String id, String phone) {
    this.id = id;
    this.phone = phone;
  }

  public String getId() {
    return id;
  }

  public String getPhone() {
    return phone;
  }

}
