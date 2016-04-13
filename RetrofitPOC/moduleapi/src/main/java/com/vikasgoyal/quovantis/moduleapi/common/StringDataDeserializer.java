package com.vikasgoyal.quovantis.moduleapi.common;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vikasgoyal.quovantis.moduleapi.core.ApiDeserializer;

import java.lang.reflect.Type;

public class StringDataDeserializer extends ApiDeserializer<String> {
    @Override
    public String deserialize(Gson gson, JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return String.valueOf(json);
    }
}
