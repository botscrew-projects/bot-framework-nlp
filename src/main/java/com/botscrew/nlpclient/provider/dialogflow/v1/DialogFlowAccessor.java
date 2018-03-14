package com.botscrew.nlpclient.provider.dialogflow.v1;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v1.domain.AIServiceContextImpl;

public class DialogFlowAccessor implements NlpEngineAccessor {

    private AIDataService dataService;

    public DialogFlowAccessor(String clientToken) {
        if (clientToken == null || clientToken.isEmpty()) {
            throw new DialogFlowException("Client access token is not present");
        }

        AIConfiguration configuration = new AIConfiguration(clientToken);
        dataService = new AIDataService(configuration);
    }

    @Override
    public NlpResponse query(String query) {
        return tryToSendQuery(new AIRequest(query), null);
    }

    @Override
    public NlpResponse query(String query, String sessionId) {
        return tryToSendQuery(new AIRequest(query), new AIServiceContextImpl(sessionId));
    }

    private NlpResponse tryToSendQuery(AIRequest request, AIServiceContext aiServiceContext) {
        try {
            AIResponse dfResponse = dataService.request(request, aiServiceContext);
            NlpResponse response = new NlpResponse(dfResponse.getResult().getAction());
            response.addParam(dfResponse);

            return response;
        } catch (AIServiceException e) {
            throw new DialogFlowException(e.getMessage(), e);
        }
    }
}
