package com.botscrew.nlpclient.provider.dialogflow.v1;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.argument.kit.ArgumentKit;
import com.botscrew.botframework.domain.argument.wrapper.SimpleArgumentWrapper;
import com.botscrew.nlpclient.domain.DialogFlowConfiguration;
import com.botscrew.nlpclient.domain.NlpAccessorConfiguration;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v1.domain.AIServiceContextImpl;

import java.util.HashMap;
import java.util.Map;

public class DialogFlowAccessor implements NlpEngineAccessor {

    private AIDataService defaultAiDataService;
    private final Map<DialogFlowConfiguration, AIDataService> aiDataServices;

    public DialogFlowAccessor(NlpAccessorConfiguration nlpAccessorConfiguration) {
        DialogFlowConfiguration dialogFlowConfiguration = (DialogFlowConfiguration) nlpAccessorConfiguration;
        if (dialogFlowConfiguration.getClientToken() == null || dialogFlowConfiguration.getClientToken().isEmpty()) {
            throw new DialogFlowException("Client access token is not present");
        }

        AIConfiguration configuration = new AIConfiguration(dialogFlowConfiguration.getClientToken());
        defaultAiDataService = new AIDataService(configuration);
        aiDataServices = new HashMap<>();
    }

    @Override
    public NlpResponse query(String query) {
        return tryToSendQuery(query, null);
    }

    @Override
    public NlpResponse query(String query, NlpAccessorConfiguration nlpAccessorConfiguration) {
        DialogFlowConfiguration configuration = (DialogFlowConfiguration) nlpAccessorConfiguration;
        AIDataService aiDataService = createIfNotExistsAndGet(configuration);
        return tryToSendQuery(query, null, aiDataService);
    }

    @Override
    public NlpResponse query(String query, String sessionId) {
        return tryToSendQuery(query, new AIServiceContextImpl(sessionId));
    }

    @Override
    public NlpResponse query(String query, String sessionId, NlpAccessorConfiguration nlpAccessorConfiguration) {
        DialogFlowConfiguration configuration = (DialogFlowConfiguration) nlpAccessorConfiguration;
        AIDataService aiDataService = createIfNotExistsAndGet(configuration);
        return tryToSendQuery(query, new AIServiceContextImpl(sessionId), aiDataService);
    }

    private AIDataService createIfNotExistsAndGet(DialogFlowConfiguration configuration) {
        return aiDataServices.computeIfAbsent(configuration,
                k -> new AIDataService(new AIConfiguration(configuration.getClientToken())));
    }

    private NlpResponse tryToSendQuery(String query, AIServiceContext aiServiceContext) {
        return tryToSendQuery(query, aiServiceContext, defaultAiDataService);
    }

    private NlpResponse tryToSendQuery(String query, AIServiceContext aiServiceContext, AIDataService aiDataService) {
        try {
            AIResponse aiResponse = aiDataService.request(new AIRequest(query), aiServiceContext);

            NlpResponse response = new NlpResponse(aiResponse.getResult().getAction());
            ArgumentKit argumentKit = response.getArgumentKit();
            argumentKit.put(ArgumentType.TEXT, new SimpleArgumentWrapper(query));
            argumentKit.put(ArgumentType.NATIVE_NLP_RESPONSE, new SimpleArgumentWrapper(aiResponse));

            aiResponse.getResult().getParameters()
                    .forEach((key, value) -> {
                        SimpleArgumentWrapper wrapper = new SimpleArgumentWrapper(value);
                        argumentKit.put(key, wrapper);
                    });

            return response;
        } catch (AIServiceException e) {
            throw new DialogFlowException(e.getMessage(), e);
        }
    }
}
