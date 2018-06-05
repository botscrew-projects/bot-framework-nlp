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

package com.botscrew.nlpclient.provider.dialogflow.v2;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.argument.kit.ArgumentKit;
import com.botscrew.botframework.domain.argument.wrapper.SimpleArgumentWrapper;
import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.exception.NlpClientException;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v2.domain.DialogFlowV2Credentials;
import com.botscrew.nlpclient.provider.dialogflow.v2.domain.QueryRequest;
import com.botscrew.nlpclient.provider.dialogflow.v2.model.Response;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation for {@link NlpEngineAccessor} for DialogFlow API V2
 */
public class DialogFlowAccessor implements NlpEngineAccessor {
    private static final String DEFAULT_SESSION_ID = "DEFAULT_SESSION_ID";
    private static final String BASE_URL = "https://dialogflow.googleapis.com";

    private final RestTemplate restTemplate;
    private final DialogFlowV2Credentials defaultCredentials;

    public DialogFlowAccessor(RestTemplate restTemplate, DialogFlowV2Credentials defaultCredentials) {
        this.restTemplate = restTemplate;
        this.defaultCredentials = defaultCredentials;
    }

    @Override
    public NlpResponse query(String query) {
        return query(query, defaultCredentials);
    }

    @Override
    public NlpResponse query(String query, NlpProviderCredentials credentials) {
        return query(query, DEFAULT_SESSION_ID, defaultCredentials);
    }

    @Override
    public NlpResponse query(String query, String sessionId) {
        return query(query, sessionId, defaultCredentials);
    }

    @Override
    public NlpResponse query(String query, String sessionId, NlpProviderCredentials credentials) {
        checkCredentials(credentials);

        String url = createDetectIntentUrl((DialogFlowV2Credentials) credentials, sessionId);
        HttpEntity<QueryRequest> request = createRequest(query, (DialogFlowV2Credentials) credentials);
        try {
            ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, Response.class);

            String intent = getIntentName(responseEntity.getBody());
            NlpResponse response = new NlpResponse(intent);

            ArgumentKit argumentKit = response.getArgumentKit();
            argumentKit.put(ArgumentType.TEXT, new SimpleArgumentWrapper(query));
            argumentKit.put(ArgumentType.ORIGINAL_RESPONSE, new SimpleArgumentWrapper(responseEntity.getBody()));

            if (responseEntity.getBody().getQueryResult().getParameters() != null) {
                responseEntity.getBody().getQueryResult().getParameters()
                        .forEach((key, value) -> {
                            SimpleArgumentWrapper wrapper = new SimpleArgumentWrapper(value);
                            argumentKit.put(key, wrapper);
                        });
            }

            return response;
        }
        catch (HttpClientErrorException e) {
            throw new DialogFlowException(e.getResponseBodyAsString(), e);
        }
    }

    private void checkCredentials(NlpProviderCredentials credentials) {
        DialogFlowV2Credentials dialogFlowV2Credentials = (DialogFlowV2Credentials) credentials;
        if (dialogFlowV2Credentials.getProjectId() == null) throw new NlpClientException("Project ID should not be null!");
        if (dialogFlowV2Credentials.getAccessToken() == null) throw new NlpClientException("Access token should not be null!");
    }

    private HttpEntity<QueryRequest> createRequest(String query, DialogFlowV2Credentials credentials) {
        QueryRequest request = new QueryRequest(query);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + credentials.getAccessToken());

        return new HttpEntity<>(request, headers);
    }

    private String createDetectIntentUrl(DialogFlowV2Credentials credentials, String sessionId) {
        return BASE_URL + "/v2/projects/" + credentials.getProjectId() + "/agent/sessions/" + sessionId + ":detectIntent";
    }

    private String getIntentName(Response response) {
        if (response.getQueryResult() != null
                && response.getQueryResult().getIntent() != null
                && response.getQueryResult().getIntent().getDisplayName() != null)
            return response.getQueryResult().getIntent().getDisplayName();
        else return response.getQueryResult().getAction();
    }
}
