package com.botscrew.nlpclient.provider.dialogflow.v2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("nlp.provider.dialog-flow.v2")
public class DialogFlowV2Properties {
    private String projectId;
    private String accessToken;
}
