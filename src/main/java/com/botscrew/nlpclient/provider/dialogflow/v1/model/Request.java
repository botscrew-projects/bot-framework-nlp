package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

    @JsonProperty("query")
    private String query;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("lang")
    private String lang;

    public Request(String query, String sessionId) {
        this.query = query;
        this.sessionId = sessionId;
        this.lang = "en";
    }
}
