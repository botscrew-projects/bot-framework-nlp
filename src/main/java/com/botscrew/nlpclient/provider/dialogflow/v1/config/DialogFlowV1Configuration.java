package com.botscrew.nlpclient.provider.dialogflow.v1.config;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.argument.composer.ArgumentsComposerFactory;
import com.botscrew.nlpclient.domain.DialogFlowConfiguration;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.interceptor.PreNlpResponseProcessingAction;
import com.botscrew.nlpclient.provider.BotFrameworkNlpClient;
import com.botscrew.nlpclient.provider.NlpClient;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.converter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(value = {DialogFlowV1Properties.class})
public class DialogFlowV1Configuration {


    @Bean
    public NlpEngineAccessor nlpEngineAccessor(DialogFlowV1Properties properties) {
        return new DialogFlowAccessor(new DialogFlowConfiguration(properties.getClientToken()));
    }

    @Bean
    public NlpClient nlpClient(NlpEngineAccessor nlpEngineAccessor,
                               IntentContainer intentContainer,
                               @Autowired(required = false) List<NlpInterceptor<PreNlpResponseProcessingAction>> preNlpResponseProcessingInterceptors) {
        registrateConverters();
        return new BotFrameworkNlpClient(nlpEngineAccessor, intentContainer, preNlpResponseProcessingInterceptors);
    }

    private void registrateConverters() {
        ArgumentsComposerFactory.putConverter(new JsonElementToComplexTypeConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToDoubleConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToIntegerConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToLongConverter());
        ArgumentsComposerFactory.putConverter(new JsonPrimitiveToStringConverter());
        ArgumentsComposerFactory.putConverter(new JsonObjectToComplexTypeConverter());
    }
}
