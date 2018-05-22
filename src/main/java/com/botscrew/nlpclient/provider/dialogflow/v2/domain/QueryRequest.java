package com.botscrew.nlpclient.provider.dialogflow.v2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryRequest {
    private QueryInput queryInput;

    public QueryRequest(String queryInput) {
        this.queryInput = new QueryInput(queryInput);
    }
}
