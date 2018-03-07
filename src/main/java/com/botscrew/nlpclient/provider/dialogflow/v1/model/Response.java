package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Response {

    private String id;
    private String lang;
    private Result result;
    private String sessionId;
    private Status status;
    private Date timestamp;
}
