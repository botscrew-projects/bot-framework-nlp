package com.botscrew.nlpclient.domain;

import java.util.Objects;

public class DialogFlowConfiguration implements NlpAccessorConfiguration {
    private final String clientToken;

    public DialogFlowConfiguration(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogFlowConfiguration that = (DialogFlowConfiguration) o;
        return Objects.equals(clientToken, that.clientToken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientToken);
    }
}
