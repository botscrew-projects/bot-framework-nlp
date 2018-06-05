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

package com.botscrew.nlpclient.provider;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.user.ChatUser;
import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.interceptor.NlpInterceptor;
import com.botscrew.nlpclient.interceptor.PreNlpResponseProcessingAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for {@link NlpClient} which sends response to Bot Framework
 */
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
