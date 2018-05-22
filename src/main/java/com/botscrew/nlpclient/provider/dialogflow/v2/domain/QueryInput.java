package com.botscrew.nlpclient.provider.dialogflow.v2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryInput {

    private TextInput text;

    public QueryInput(String text) {
        this.text = new TextInput(text);
    }
}
