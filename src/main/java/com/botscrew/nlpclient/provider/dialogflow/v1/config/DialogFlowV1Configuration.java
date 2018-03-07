package com.botscrew.nlpclient.provider.dialogflow.v1.config;

import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(value = {DialogFlowV1Properties.class})
public class DialogFlowV1Configuration {

    @Bean
    public DialogFlowClient dialogFlowClient(DialogFlowV1Properties properties, RestTemplate restTemplate) {
        if (properties.getClientToken() == null || properties.getClientToken().isEmpty()) {
            throw new DialogFlowException("Client token cannot be empty!");
        }

        return new DialogFlowClient(restTemplate, properties.getClientToken(), properties.getV(), properties.getBaseUrl());
    }
}
