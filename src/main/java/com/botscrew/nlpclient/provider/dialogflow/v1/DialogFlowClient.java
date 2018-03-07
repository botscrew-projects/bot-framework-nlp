package com.botscrew.nlpclient.provider.dialogflow.v1;

import com.botscrew.nlpclient.provider.dialogflow.exception.DialogFlowException;
import com.botscrew.nlpclient.provider.dialogflow.v1.model.Request;
import com.botscrew.nlpclient.provider.dialogflow.v1.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class DialogFlowClient {

    private static final String DEFAULT_SESSION_ID = "default_session_id";

    private final RestTemplate restTemplate;
    private final String clientToken;
    private final String version;
    private final String baseUrl;
    private final String queryUrl;

    public DialogFlowClient(RestTemplate restTemplate, String clientToken, String version, String baseUrl) {
        this.restTemplate = restTemplate;
        this.clientToken = clientToken;
        this.version = version;
        this.baseUrl = baseUrl;
        this.queryUrl = baseUrl + "/query";
    }

    public Response query(String query) {
        return query(query, DEFAULT_SESSION_ID);
    }

    public Response query(String query, String sessionId) {
        Request request = new Request(query, sessionId);
        HttpEntity httpEntity = buildEntity(request);
        try {
            return makePost(httpEntity);
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new DialogFlowException(e.getResponseBodyAsString());
        }
    }

    private Response makePost(HttpEntity entity) {
        String url = queryUrl + "?v=" + version;

        ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Response.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new DialogFlowException(responseEntity.getStatusCodeValue() + " : " + responseEntity.getStatusCode().getReasonPhrase());
    }

    private HttpEntity buildEntity(Request request) {
        return new HttpEntity<>(request, buildHeaders());
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + clientToken);

        return headers;
    }
}
