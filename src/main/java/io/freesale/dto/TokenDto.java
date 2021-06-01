package io.freesale.dto;

public class TokenDto {

  private final String accessToken;
  private final String tokenType;

  public TokenDto(String accessToken, String tokenType) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

}
