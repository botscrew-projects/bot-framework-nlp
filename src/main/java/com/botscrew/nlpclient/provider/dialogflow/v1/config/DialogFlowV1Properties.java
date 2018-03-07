package com.botscrew.nlpclient.provider.dialogflow.v1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("nlp.provider.dialog-flow.v1")
public class DialogFlowV1Properties {

    private String clientToken;
    private String developerToken;
    private String baseUrl = "https://api.dialogflow.com/v1";
    private String v = "20170712";
}
