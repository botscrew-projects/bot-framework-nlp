package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Metadata {

    private String intentId;
    private String intentName;
    private boolean webhookForSlotFillingUsed;
    private boolean webhookUsed;
}
