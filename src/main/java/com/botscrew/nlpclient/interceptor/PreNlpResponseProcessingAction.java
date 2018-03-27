package com.botscrew.nlpclient.interceptor;

public class PreNlpResponseProcessingAction implements NlpAction {
    private final String processedQuery;
    private final String intentName;


    public PreNlpResponseProcessingAction(String processedQuery, String intentName) {
        this.processedQuery = processedQuery;
        this.intentName = intentName;
    }

    public String getProcessedQuery() {
        return processedQuery;
    }

    public String getIntentName() {
        return intentName;
    }
}
