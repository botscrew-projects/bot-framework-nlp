package com.botscrew.nlpclient.provider.dialogflow.v1.converter;

import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.botscrew.botframework.model.ArgumentType;
import com.google.gson.JsonElement;

public class JsonElementToLongConverter implements ArgumentConverter<JsonElement> {
    private static final ConverterKey KEY = ConverterKey.of(JsonElement.class, ArgumentType.PARAM_LONG);

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(JsonElement jsonElement, Class<?> originalType) {
        return jsonElement.getAsLong();
    }
}
