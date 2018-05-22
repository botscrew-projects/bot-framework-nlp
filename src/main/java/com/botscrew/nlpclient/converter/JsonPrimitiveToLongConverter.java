package com.botscrew.nlpclient.converter;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.google.gson.JsonPrimitive;

public class JsonPrimitiveToLongConverter implements ArgumentConverter<JsonPrimitive> {
    private static final ConverterKey KEY = ConverterKey.of(JsonPrimitive.class, ArgumentType.PARAM_LONG);

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(JsonPrimitive jsonElement, Class<?> originalType) {
        return jsonElement.getAsLong();
    }
}
