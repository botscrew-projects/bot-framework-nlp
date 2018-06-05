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

package com.botscrew.nlpclient.config;

import com.botscrew.nlpclient.domain.NlpResponse;
import com.botscrew.nlpclient.provider.NlpEngineAccessor;
import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowAccessor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfiguration
public class IntentContainerConfiguration {
    @Bean("defaultNlpEngineAccessor")
    @Primary
    public NlpEngineAccessor nlpEngineAccessor() {
        DialogFlowAccessor mock = Mockito.mock(DialogFlowAccessor.class);
        when(mock.query(anyString())).thenReturn(new NlpResponse("intent"));
        when(mock.query(anyString(), anyString())).thenReturn(new NlpResponse("intent"));

        return mock;
    }
}
