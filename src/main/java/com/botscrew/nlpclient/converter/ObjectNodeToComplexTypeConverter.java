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

package com.botscrew.nlpclient.converter;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.botscrew.botframework.exception.ProcessorInnerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectNodeToComplexTypeConverter implements ArgumentConverter<ObjectNode> {
    private static final ConverterKey KEY = ConverterKey.of(ObjectNode.class, ArgumentType.COMPLEX_TYPE);
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(ObjectNode jsonNode, Class<?> originalType) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, originalType);
        } catch (JsonProcessingException e) {
            throw new ProcessorInnerException("Cannot convert JsonNode to" + originalType.getName());
        }
    }
}
