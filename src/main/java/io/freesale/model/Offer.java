package io.freesale.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Document(collection = "offers")
public class Offer {

    private final String id;
    private final String amount;
    private final Status status;
    private final List<String> imageUrls;
    private final String requestId;
    private final String userId;

    public Offer(String id, String amount, Status status, List<String> imageUrls, String requestId, String userId) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.imageUrls = imageUrls;
        this.requestId = requestId;
        this.userId = userId;
    }

    public static Offer of(String amount, Status status, String requestId, String userId) {
        return new Offer(null, amount, status, Collections.emptyList(), requestId, userId);
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

    public List<String> getImageUrls() {
        return imageUrls;
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
