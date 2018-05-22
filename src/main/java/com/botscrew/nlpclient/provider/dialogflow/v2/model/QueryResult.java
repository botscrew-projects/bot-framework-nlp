package com.botscrew.nlpclient.provider.dialogflow.v2.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QueryResult {
    private String queryText;
    private String action;
    private Map<String, JsonNode> parameters;
    private Boolean allRequiredParamsPresent;
    private String fulfillmentText;
    private Intent intent;
    private Integer intentDetectionConfidence;
    private String languageCode;
}
