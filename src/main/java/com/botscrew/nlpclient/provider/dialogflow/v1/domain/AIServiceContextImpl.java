package com.botscrew.nlpclient.provider.dialogflow.v1.domain;

import ai.api.AIServiceContext;

import java.util.TimeZone;

public class AIServiceContextImpl implements AIServiceContext {
    private final String sessionId;
    private final TimeZone timeZone;

    public AIServiceContextImpl(String sessionId) {
        this.sessionId = sessionId;
        timeZone = TimeZone.getDefault();
    }

    public AIServiceContextImpl(String sessionId, TimeZone timeZone) {
        this.sessionId = sessionId;
        this.timeZone = timeZone;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public TimeZone getTimeZone() {
        return timeZone;
    }
}
