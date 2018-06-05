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

package com.botscrew.nlpclient.interceptor;

import com.botscrew.botframework.domain.user.ChatUser;

public class PreNlpResponseProcessingAction implements NlpAction {
    private final ChatUser user;
    private final String processedQuery;
    private final String intentName;


    public PreNlpResponseProcessingAction(ChatUser user, String processedQuery, String intentName) {
        this.user = user;
        this.processedQuery = processedQuery;
        this.intentName = intentName;
    }

    public String getProcessedQuery() {
        return processedQuery;
    }

    public String getIntentName() {
        return intentName;
    }

    public ChatUser getUser() {
        return user;
    }
}
