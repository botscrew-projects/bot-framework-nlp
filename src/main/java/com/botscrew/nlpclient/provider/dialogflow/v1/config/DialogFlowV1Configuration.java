package com.botscrew.nlpclient.provider.dialogflow.v1.config;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.argument.composer.ArgumentsComposerFactory;
import com.botscrew.nlpclient.provider.BotFrameworkNlpClient;
import com.botscrew.nlpclient.provider.NlpClient;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.converter.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {DialogFlowV1Properties.class})
public class DialogFlowV1Configuration {


    @Bean
    public NlpEngineAccessor nlpEngineAccessor(DialogFlowV1Properties properties) {
        return new DialogFlowAccessor(properties.getClientToken());
    }

    @Bean
    public NlpClient nlpClient(NlpEngineAccessor nlpEngineAccessor, IntentContainer intentContainer) {
        registrateConverters();
        return new BotFrameworkNlpClient(nlpEngineAccessor, intentContainer);
    }

    private void registrateConverters() {
        ArgumentsComposerFactory.putConverter(new JsonElementToComplexTypeConverter());
        ArgumentsComposerFactory.putConverter(new JsonElementToDoubleConverter());
        ArgumentsComposerFactory.putConverter(new JsonElementToIntegerConverter());
        ArgumentsComposerFactory.putConverter(new JsonElementToLongConverter());
        ArgumentsComposerFactory.putConverter(new JsonElementToStringConverter());
        ArgumentsComposerFactory.putConverter(new JsonObjectToComplexTypeConverter());
    }
}
