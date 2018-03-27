package com.botscrew.nlpclient.config;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.method.group.IntentHandlingMethodGroup;
import com.botscrew.nlpclient.PreNlpResponseProcessingInterceptorsTests;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowAccessor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfiguration
public class IntentContainerConfiguration {
    @Bean
    @Primary
    public NlpEngineAccessor nlpEngineAccessor() {
        DialogFlowAccessor mock = Mockito.mock(DialogFlowAccessor.class);
        when(mock.query(anyString())).thenReturn(new NlpResponse("intent"));
        when(mock.query(anyString(), anyString())).thenReturn(new NlpResponse("intent"));

        return mock;
    }
}
