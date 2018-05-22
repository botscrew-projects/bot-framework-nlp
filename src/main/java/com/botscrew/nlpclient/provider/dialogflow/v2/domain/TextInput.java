package com.botscrew.nlpclient.provider.dialogflow.v2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextInput {
    private String text;
    private String languageCode = "en";

    public TextInput(String text) {
        this.text = text;
    }
}
