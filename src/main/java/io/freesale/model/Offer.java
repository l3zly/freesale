package io.freesale.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offers")
public class Offer {

    private final String id;
    private final String amount;
    private final Status status;
    private final String requestId;
    private final String userId;

    public Offer(String id, String amount, Status status, String requestId, String userId) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.requestId = requestId;
        this.userId = userId;
    }

    public static Offer of(String amount, Status status, String requestId, String userId) {
        return new Offer(null, amount, status, requestId, userId);
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getUserId() {
        return userId;
    }

    public enum Status {
        ACCEPTED,
        DECLINED,
        PENDING
    }

}
