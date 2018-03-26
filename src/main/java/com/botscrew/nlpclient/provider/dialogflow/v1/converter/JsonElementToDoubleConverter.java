package com.botscrew.nlpclient.provider.dialogflow.v1.converter;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.google.gson.JsonElement;

public class JsonElementToDoubleConverter implements ArgumentConverter<JsonElement> {
    private static final ConverterKey KEY = ConverterKey.of(JsonElement.class, ArgumentType.PARAM_DOUBLE);

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(JsonElement jsonElement, Class<?> originalType) {
        return jsonElement.getAsDouble();
    }
}
