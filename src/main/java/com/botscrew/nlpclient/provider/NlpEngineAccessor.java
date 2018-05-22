package com.botscrew.nlpclient.provider;

import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;

public interface NlpEngineAccessor {

    NlpResponse query(String query);

    NlpResponse query(String query, NlpProviderCredentials nlpProviderCredentials);

    NlpResponse query(String query, String sessionId);

    NlpResponse query(String query, String sessionId, NlpProviderCredentials nlpProviderCredentials);
}
