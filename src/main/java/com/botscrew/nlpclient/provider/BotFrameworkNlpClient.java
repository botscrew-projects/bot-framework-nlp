package com.botscrew.nlpclient.provider;

import com.botscrew.botframework.container.IntentContainer;
import com.botscrew.botframework.domain.user.ChatUser;
import com.botscrew.nlpclient.domain.NlpResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotFrameworkNlpClient implements NlpClient {

    private final NlpEngineAccessor nlpEngineAccessor;
    private final IntentContainer intentContainer;

    @Override
    public void query(ChatUser user, String query) {
        NlpResponse response = nlpEngineAccessor.query(query);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }

    @Override
    public void query(ChatUser user, String query, String sessionId) {
        NlpResponse response = nlpEngineAccessor.query(query, sessionId);
        intentContainer.process(user, response.getIntent(), response.getArgumentKit());
    }
}
