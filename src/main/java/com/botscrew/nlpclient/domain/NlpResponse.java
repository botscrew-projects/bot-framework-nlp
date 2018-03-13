package com.botscrew.nlpclient.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NlpResponse {

    private final String intent;
    private final Map<Class, Object> parameters;

    public NlpResponse(String intent) {
        this.intent = intent;
        parameters = new HashMap<>();
    }

    public void addParam(Object param) {
        parameters.put(param.getClass(), param);
    }
}
