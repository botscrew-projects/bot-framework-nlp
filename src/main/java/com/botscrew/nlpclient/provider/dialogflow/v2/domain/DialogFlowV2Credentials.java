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
