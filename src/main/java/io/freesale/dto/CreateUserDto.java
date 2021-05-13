package io.freesale.dto;

public class CreateUserDto {

    private final String phone;
    private final String password;

    public CreateUserDto(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

}
