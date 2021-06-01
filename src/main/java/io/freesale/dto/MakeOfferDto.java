package io.freesale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MakeOfferDto {

    private final String amount;

    public MakeOfferDto(@JsonProperty("amount") String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

}
