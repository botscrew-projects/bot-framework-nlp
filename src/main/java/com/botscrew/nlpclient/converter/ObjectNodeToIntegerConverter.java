package com.botscrew.nlpclient.converter;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectNodeToIntegerConverter implements ArgumentConverter<ObjectNode> {
    private static final ConverterKey KEY = ConverterKey.of(ObjectNode.class, ArgumentType.PARAM_INTEGER);

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(ObjectNode jsonNode, Class<?> originalType) {
        return jsonNode.asInt();
    }
}
