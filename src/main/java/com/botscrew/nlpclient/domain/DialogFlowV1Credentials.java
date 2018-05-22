package com.botscrew.nlpclient.domain;

import java.util.Objects;

public class DialogFlowV1Credentials implements NlpProviderCredentials {
    private final String clientToken;

    public DialogFlowV1Credentials(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogFlowV1Credentials that = (DialogFlowV1Credentials) o;
        return Objects.equals(clientToken, that.clientToken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientToken);
    }
}
