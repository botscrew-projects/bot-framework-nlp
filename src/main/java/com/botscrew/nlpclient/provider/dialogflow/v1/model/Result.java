package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Result {

    private String action;
    private boolean actionIncomplete;
    private List<String> contexts;
    private Fulfillment fulfillment;
    private Metadata metadata;
    private Map<String, Object> parameters;
    private String resolvedQuery;
    private Integer score;
    private String source;

}
