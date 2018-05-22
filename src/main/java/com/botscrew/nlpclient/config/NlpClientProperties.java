package com.botscrew.nlpclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("nlp.provider")
public class NlpClientProperties {
    public static final String DIALOG_FLOW_V1 = "DIALOG_FLOW_V1";
    public static final String DIALOG_FLOW_V2 = "DIALOG_FLOW_V2";

    private String type = DIALOG_FLOW_V2;
}
