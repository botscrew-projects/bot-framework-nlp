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

package com.botscrew.nlpclient.provider.dialogflow.v2.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class QueryResult {
    private String queryText;
    private String action;
    private Map<String, JsonNode> parameters;
    private Boolean allRequiredParamsPresent;
    private String fulfillmentText;
    private List<FulfillmentMessage> fulfillmentMessages;
    private Intent intent;
    private Integer intentDetectionConfidence;
    private String languageCode;

    @Getter
    @Setter
    private static class FulfillmentMessage {
        private Text text;

        @Getter
        @Setter
        private static class Text {
            private String[] text;
        }
    }

    public List<String> getTextResponseList() {
        return fulfillmentMessages.stream()
                .flatMap(message -> Arrays.stream(message.getText().getText()))
                .collect(Collectors.toList());
    }
}
