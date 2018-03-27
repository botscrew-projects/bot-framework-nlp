package com.botscrew.nlpclient.provider;


import com.botscrew.botframework.domain.user.ChatUser;
import com.botscrew.nlpclient.domain.NlpAccessorConfiguration;

public interface NlpClient {

    void query(ChatUser user, String query);

    void query(ChatUser user, String query, NlpAccessorConfiguration configuration);

    void query(ChatUser user, String query, String sessionId);

    void query(ChatUser user, String query, String sessionId, NlpAccessorConfiguration configuration);
}
