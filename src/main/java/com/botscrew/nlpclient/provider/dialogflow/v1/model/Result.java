package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Result {

    private String action;
    private boolean actionIncomplete;
    private List<String> contexts;
    private Fulfillment fulfillment;
    private Metadata metadata;
    private Parameters parameters;
    private String resolvedQuery;
    private Integer score;
    private String source;

}
