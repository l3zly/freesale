package io.freesale.exception;

public class RequestNotFoundException extends RuntimeException {

  public RequestNotFoundException() {
    super("Request not found");
  }

}
