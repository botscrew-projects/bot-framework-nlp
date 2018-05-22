package com.botscrew.nlpclient.converter;

import com.botscrew.botframework.domain.argument.ArgumentType;
import com.botscrew.botframework.domain.converter.ArgumentConverter;
import com.botscrew.botframework.domain.converter.ConverterKey;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonElementToComplexTypeConverter implements ArgumentConverter<JsonElement> {
    private static final ConverterKey KEY = ConverterKey.of(JsonElement.class, ArgumentType.COMPLEX_TYPE);
    private static final Gson GSON = new Gson();

    @Override
    public ConverterKey getKey() {
        return KEY;
    }

    @Override
    public Object convert(JsonElement jsonElement, Class<?> originalType) {
        return GSON.fromJson(jsonElement, originalType);
    }
}
