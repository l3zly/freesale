package io.freesale.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "requests")
public class Request {

    private final String id;
    private final String title;
    private final List<String> offerIds;
    private final String userId;

    public Request(String id, String title, List<String> offerIds, String userId) {
        this.id = id;
        this.title = title;
        this.offerIds = offerIds;
        this.userId = userId;
    }

    public static Request of(String title, List<String> offerIds, String userId) {
        return new Request(null, title, offerIds, userId);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    public String getUserId() {
        return userId;
    }

}
