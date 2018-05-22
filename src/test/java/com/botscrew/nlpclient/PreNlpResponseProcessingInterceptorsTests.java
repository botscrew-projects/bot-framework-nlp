package com.botscrew.nlpclient;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.nlpclient.config.IntentContainerConfiguration;
import com.botscrew.nlpclient.config.NlpClientConfiguration;
import com.botscrew.nlpclient.config.NlpClientRestTemplateConfiguration;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.interceptor.PreNlpResponseProcessingAction;
import com.botscrew.nlpclient.provider.NlpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NlpClientConfiguration.class, NlpClientRestTemplateConfiguration.class, IntentContainerConfiguration.class})
@TestPropertySource(properties = {
        "nlp.provider.dialog-flow.v1.client-token=1",
        "nlp.provider.dialog-flow.v2.access-token=1",
        "nlp.provider.dialog-flow.v2.project-id=1"})
@Configuration
public class PreNlpResponseProcessingInterceptorsTests {
    @MockBean
    private NlpInterceptor<PreNlpResponseProcessingAction> nlpResponseProcessingActionNlpInterceptor;
    @MockBean
    private IntentContainer intentContainer;
    @Autowired
    private NlpClient nlpClient;

    @Test
    public void shouldRunPreProcessingInterceptor() {
        nlpClient.query(() -> "1", "HI!");

        verify(nlpResponseProcessingActionNlpInterceptor, times(1)).onAction(any());
    }
}
