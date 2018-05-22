package com.botscrew.nlpclient.provider.dialogflow.v2.domain;

import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import lombok.Getter;

@Getter
public class DialogFlowV2Credentials implements NlpProviderCredentials {
    private final String projectId;
    private final String accessToken;

    public DialogFlowV2Credentials(String projectId, String accessToken) {
        this.projectId = projectId;
        this.accessToken = accessToken;
    }
}
