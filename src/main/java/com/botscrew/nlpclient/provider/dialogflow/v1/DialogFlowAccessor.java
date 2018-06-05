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

package com.botscrew.nlpclient.provider.dialogflow.v1;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.argument.kit.ArgumentKit;
import com.botscrew.botframework.domain.argument.wrapper.SimpleArgumentWrapper;
import com.botscrew.nlpclient.domain.DialogFlowV1Credentials;
import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v1.domain.AIServiceContextImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for {@link NlpEngineAccessor} for DialogFlow API V1
 */
public class DialogFlowAccessor implements NlpEngineAccessor {

    private AIDataService defaultAiDataService;
    private final Map<DialogFlowV1Credentials, AIDataService> aiDataServices;

    public DialogFlowAccessor(NlpProviderCredentials nlpProviderCredentials) {
        DialogFlowV1Credentials dialogFlowV1Credentials = (DialogFlowV1Credentials) nlpProviderCredentials;
        if (dialogFlowV1Credentials.getClientToken() == null || dialogFlowV1Credentials.getClientToken().isEmpty()) {
            throw new DialogFlowException("Client access token is not present");
        }

        AIConfiguration configuration = new AIConfiguration(dialogFlowV1Credentials.getClientToken());
        defaultAiDataService = new AIDataService(configuration);
        aiDataServices = new HashMap<>();
    }

    @Override
    public NlpResponse query(String query) {
        return tryToSendQuery(query, null);
    }

    @Override
    public NlpResponse query(String query, NlpProviderCredentials nlpProviderCredentials) {
        DialogFlowV1Credentials configuration = (DialogFlowV1Credentials) nlpProviderCredentials;
        AIDataService aiDataService = createIfNotExistsAndGet(configuration);
        return tryToSendQuery(query, null, aiDataService);
    }

    @Override
    public NlpResponse query(String query, String sessionId) {
        return tryToSendQuery(query, new AIServiceContextImpl(sessionId));
    }

    @Override
    public NlpResponse query(String query, String sessionId, NlpProviderCredentials nlpProviderCredentials) {
        DialogFlowV1Credentials configuration = (DialogFlowV1Credentials) nlpProviderCredentials;
        AIDataService aiDataService = createIfNotExistsAndGet(configuration);
        return tryToSendQuery(query, new AIServiceContextImpl(sessionId), aiDataService);
    }

    private AIDataService createIfNotExistsAndGet(DialogFlowV1Credentials configuration) {
        return aiDataServices.computeIfAbsent(configuration,
                k -> new AIDataService(new AIConfiguration(configuration.getClientToken())));
    }

    private NlpResponse tryToSendQuery(String query, AIServiceContext aiServiceContext) {
        return tryToSendQuery(query, aiServiceContext, defaultAiDataService);
    }

    private NlpResponse tryToSendQuery(String query, AIServiceContext aiServiceContext, AIDataService aiDataService) {
        try {
            AIResponse aiResponse = aiDataService.request(new AIRequest(query), aiServiceContext);

            String intent = getIntentName(aiResponse);
            NlpResponse response = new NlpResponse(intent);

            ArgumentKit argumentKit = response.getArgumentKit();
            argumentKit.put(ArgumentType.TEXT, new SimpleArgumentWrapper(query));
            argumentKit.put(ArgumentType.ORIGINAL_RESPONSE, new SimpleArgumentWrapper(aiResponse));

            if (aiResponse.getResult().getParameters() != null) {
                aiResponse.getResult().getParameters()
                        .forEach((key, value) -> {
                            SimpleArgumentWrapper wrapper = new SimpleArgumentWrapper(value);
                            argumentKit.put(key, wrapper);
                        });
            }

            return response;
        } catch (AIServiceException e) {
            throw new DialogFlowException(e.getMessage(), e);
        }
    }

    private String getIntentName(AIResponse aiResponse) {
        Metadata metadata = aiResponse.getResult().getMetadata();
        if (metadata != null && metadata.getIntentName() != null)
            return metadata.getIntentName();
        else {
            return aiResponse.getResult().getAction();
        }
    }
}
