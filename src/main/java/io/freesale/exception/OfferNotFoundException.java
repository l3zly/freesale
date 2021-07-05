package io.freesale.exception;

public class OfferNotFoundException extends RuntimeException {

  public OfferNotFoundException() {
    super("Offer not found");
  }

}
