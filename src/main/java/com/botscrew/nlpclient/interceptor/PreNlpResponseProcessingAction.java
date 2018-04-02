package com.botscrew.nlpclient.interceptor;

import com.botscrew.botframework.domain.user.ChatUser;

public class PreNlpResponseProcessingAction implements NlpAction {
    private final ChatUser user;
    private final String processedQuery;
    private final String intentName;


    public PreNlpResponseProcessingAction(ChatUser user, String processedQuery, String intentName) {
        this.user = user;
        this.processedQuery = processedQuery;
        this.intentName = intentName;
    }

    public String getProcessedQuery() {
        return processedQuery;
    }

    public String getIntentName() {
        return intentName;
    }

    public ChatUser getUser() {
        return user;
    }
}
