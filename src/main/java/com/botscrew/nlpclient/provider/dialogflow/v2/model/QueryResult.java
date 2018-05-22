package com.botscrew.nlpclient.provider.dialogflow.v2.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class QueryResult {
    private String queryText;
    private String action;
    private Map<String, JsonNode> parameters;
    private Boolean allRequiredParamsPresent;
    private String fulfillmentText;
    private List<FulfillmentMessage> fulfillmentMessages;
    private Intent intent;
    private Integer intentDetectionConfidence;
    private String languageCode;

    @Getter
    @Setter
    private static class FulfillmentMessage {
        private Text text;

        @Getter
        @Setter
        private static class Text {
            private String[] text;
        }
    }

    public List<String> getTextResponseList() {
        return fulfillmentMessages.stream()
                .flatMap(message -> Arrays.stream(message.getText().getText()))
                .collect(Collectors.toList());
    }
}
