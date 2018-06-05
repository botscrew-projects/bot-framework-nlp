/*
 * Copyright 2018 BotsCrew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.botscrew.nlpclient.config;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.argument.composer.ArgumentsComposerFactory;
import com.botscrew.nlpclient.converter.*;
import com.botscrew.nlpclient.domain.DialogFlowV1Credentials;
import com.botscrew.nlpclient.exception.NlpClientException;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.interceptor.PreNlpResponseProcessingAction;
import com.botscrew.nlpclient.provider.BotFrameworkNlpClient;
import com.botscrew.nlpclient.provider.NlpClient;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.config.DialogFlowV1Properties;
import com.botscrew.nlpclient.provider.dialogflow.v2.config.DialogFlowV2Properties;
import com.botscrew.nlpclient.provider.dialogflow.v2.domain.DialogFlowV2Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Main Spring configuration for NLP module
 */
@Configuration
@EnableConfigurationProperties(value = {
        NlpClientProperties.class,
        DialogFlowV1Properties.class,
        DialogFlowV2Properties.class
})
public class NlpClientConfiguration {

    private final NlpClientProperties nlpClientProperties;
    private final DialogFlowV1Properties dialogFlowV1Properties;
    private final DialogFlowV2Properties dialogFlowV2Properties;

    public NlpClientConfiguration(NlpClientProperties nlpClientProperties,
                                  DialogFlowV1Properties dialogFlowV1Properties,
                                  DialogFlowV2Properties dialogFlowV2Properties) {
        this.nlpClientProperties = nlpClientProperties;
        this.dialogFlowV1Properties = dialogFlowV1Properties;
        this.dialogFlowV2Properties = dialogFlowV2Properties;
    }

    @PostConstruct
    public void init() {
        registrateConverters();
    }

    @Bean("defaultNlpEngineAccessor")
    public NlpEngineAccessor defaultNlpEngineAccessor(RestTemplate restTemplate) {
        switch (nlpClientProperties.getType()) {
            case NlpClientProperties.DIALOG_FLOW_V1:
                return createDialogFlowV1Accessor();
            case NlpClientProperties.DIALOG_FLOW_V2:
                return createDialogFlowV2Accessor(restTemplate);
            default:
                throw new NlpClientException("Incorrect value for nlp accessor type: " + nlpClientProperties.getType());
        }
    }

    private NlpEngineAccessor createDialogFlowV1Accessor() {
        DialogFlowV1Credentials credentials = new DialogFlowV1Credentials(dialogFlowV1Properties.getClientToken());
        return new DialogFlowAccessor(credentials);
    }

    private NlpEngineAccessor createDialogFlowV2Accessor(RestTemplate restTemplate) {
        DialogFlowV2Credentials credentials = new DialogFlowV2Credentials(
                dialogFlowV2Properties.getProjectId(),
                dialogFlowV2Properties.getAccessToken());

        return new com.botscrew.nlpclient.provider.dialogflow.v2.DialogFlowAccessor(restTemplate, credentials);
    }

    @Bean
    public NlpClient nlpClient(@Qualifier("defaultNlpEngineAccessor") NlpEngineAccessor nlpEngineAccessor,
                               IntentContainer intentContainer,
                               @Autowired(required = false) List<NlpInterceptor<PreNlpResponseProcessingAction>> preNlpResponseProcessingInterceptors) {
        return new BotFrameworkNlpClient(nlpEngineAccessor, intentContainer, preNlpResponseProcessingInterceptors);
    }


    private void registrateConverters() {
        ArgumentsComposerFactory.putConverter(new JsonElementToComplexTypeConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToDoubleConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToIntegerConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToLongConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToStringConverter());
        ArgumentsComposerFactory.putConverter(new JsonObjectToComplexTypeConverter());
        ArgumentsComposerFactory.putConverter(new ObjectNodeToComplexTypeConverter());
        ArgumentsComposerFactory.putConverter(new ObjectNodeToStringConverter());
        ArgumentsComposerFactory.putConverter(new ObjectNodeToLongConverter());
        ArgumentsComposerFactory.putConverter(new ObjectNodeToIntegerConverter());
        ArgumentsComposerFactory.putConverter(new ObjectNodeToDoubleConverter());
    }
}