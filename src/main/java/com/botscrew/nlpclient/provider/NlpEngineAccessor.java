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

package com.botscrew.nlpclient.provider;

import com.botscrew.nlpclient.domain.NlpProviderCredentials;
import com.botscrew.nlpclient.domain.NlpResponse;

/**
 * Abstraction describes the way to communicate with nlp providers with getting direct response
 */
public interface NlpEngineAccessor {

    NlpResponse query(String query);

    NlpResponse query(String query, NlpProviderCredentials nlpProviderCredentials);

    NlpResponse query(String query, String sessionId);

    NlpResponse query(String query, String sessionId, NlpProviderCredentials nlpProviderCredentials);
}
