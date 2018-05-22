package com.botscrew.nlpclient.provider;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.user.ChatUser;
import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.interceptor.PreNlpResponseProcessingAction;

import java.util.ArrayList;
import java.util.List;

public class BotFrameworkNlpClient implements NlpClient {

    private final NlpEngineAccessor nlpEngineAccessor;
    private final IntentContainer intentContainer;
    private final List<NlpInterceptor<PreNlpResponseProcessingAction>> preNlpResponseProcessingInterceptors;

    public BotFrameworkNlpClient(NlpEngineAccessor nlpEngineAccessor,
                                 IntentContainer intentContainer,
                                 List<NlpInterceptor<PreNlpResponseProcessingAction>> preNlpResponseProcessingInterceptors) {
        this.nlpEngineAccessor = nlpEngineAccessor;
        this.intentContainer = intentContainer;
        this.preNlpResponseProcessingInterceptors = preNlpResponseProcessingInterceptors != null
                ? preNlpResponseProcessingInterceptors
                : new ArrayList<>();
    }


    @Override
    public void query(ChatUser user, String query) {
        NlpResponse response = nlpEngineAccessor.query(query);
        runPreNlpResponseProcessingActions(user, query, response);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }

    @Override
    public void query(ChatUser user, String query, NlpProviderCredentials configuration) {
        NlpResponse response = nlpEngineAccessor.query(query, configuration);
        runPreNlpResponseProcessingActions(user, query, response);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }

    @Override
    public void query(ChatUser user, String query, String sessionId) {
        NlpResponse response = nlpEngineAccessor.query(query, sessionId);
        runPreNlpResponseProcessingActions(user, query, response);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }

    @Override
    public void query(ChatUser user, String query, String sessionId, NlpProviderCredentials configuration) {
        NlpResponse response = nlpEngineAccessor.query(query, sessionId, configuration);
        runPreNlpResponseProcessingActions(user, query, response);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }

    private void runPreNlpResponseProcessingActions(ChatUser user, String query, NlpResponse response) {
        for (NlpInterceptor<PreNlpResponseProcessingAction> interceptor : preNlpResponseProcessingInterceptors) {
            interceptor.onAction(new PreNlpResponseProcessingAction(user, query, response.getIntent()));
        }
    }
}
