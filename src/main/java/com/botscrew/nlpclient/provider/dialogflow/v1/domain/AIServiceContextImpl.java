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

package com.botscrew.nlpclient.provider.dialogflow.v1.domain;

import ai.api.AIServiceContext;

import java.util.TimeZone;

public class AIServiceContextImpl implements AIServiceContext {
    private final String sessionId;
    private final TimeZone timeZone;

    public AIServiceContextImpl(String sessionId) {
        this.sessionId = sessionId;
        timeZone = TimeZone.getDefault();
    }

    public AIServiceContextImpl(String sessionId, TimeZone timeZone) {
        this.sessionId = sessionId;
        this.timeZone = timeZone;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public TimeZone getTimeZone() {
        return timeZone;
    }
}
