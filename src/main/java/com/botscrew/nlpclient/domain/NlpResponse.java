package com.botscrew.nlpclient.domain;

import com.botscrew.botframework.domain.argument.kit.ArgumentKit;
import com.botscrew.botframework.domain.argument.kit.SimpleArgumentKit;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NlpResponse {

    private final String intent;
    private final ArgumentKit argumentKit;

    public NlpResponse(String intent) {
        this.intent = intent;
        argumentKit = new SimpleArgumentKit();
    }

    public String getIntent() {
        return intent;
    }

    public ArgumentKit getArgumentKit() {
        return argumentKit;
    }
}
