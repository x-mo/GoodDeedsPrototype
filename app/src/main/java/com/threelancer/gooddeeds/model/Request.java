package com.threelancer.gooddeeds.model;

import com.threelancer.gooddeeds.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class Request {

    private String need, offer, description, requirements;
    private String requestorName, requestorEmail;
    private HashMap<String, Object> timestampCreated;

    public Request() {
    }

    public Request(String need, String offer, String description, String requirements, String requestorName, String requestorEmail, HashMap<String, Object> timestampCreated) {
        this.need = need;
        this.offer = offer;
        this.description = description;
        this.requirements = requirements;
        this.requestorName = requestorName;
        this.requestorEmail = requestorEmail;
        this.timestampCreated = timestampCreated;
    }

    public String getNeed() {
        return need;
    }

    public String getOffer() {
        return offer;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public String getRequestorEmail() {
        return requestorEmail;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampCreated.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

}
