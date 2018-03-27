package com.botscrew.nlpclient.provider;

import com.botscrew.nlpclient.domain.NlpAccessorConfiguration;
import com.botscrew.nlpclient.domain.NlpResponse;

public interface NlpEngineAccessor {

    NlpResponse query(String query);

    NlpResponse query(String query, NlpAccessorConfiguration nlpAccessorConfiguration);

    NlpResponse query(String query, String sessionId);

    NlpResponse query(String query, String sessionId, NlpAccessorConfiguration nlpAccessorConfiguration);
}
