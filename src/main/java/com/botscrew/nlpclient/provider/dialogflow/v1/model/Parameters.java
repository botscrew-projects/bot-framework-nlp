package com.botscrew.nlpclient.provider.dialogflow.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Parameters {

    private Map<String, List<String>> parameters;
}
