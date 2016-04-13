package com.vikasgoyal.quovantis.moduleapi.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * ApiDeserializer is implementation for api response in case of custom mapper if their is any
 * error in api response then it will identify it and pass the api exception for that instead of result
 *
 * @param <RESULT> Type of response required in result
 */
public abstract class ApiDeserializer<RESULT> implements com.google.gson.JsonDeserializer<RESULT> {
    private final static Gson GSON = new GsonBuilder().serializeNulls().create();

    @Override
    public final RESULT deserialize(JsonElement json, Type typeOfT
            , JsonDeserializationContext context) throws JsonParseException {
        //Need to implement as per api response structure
//        JsonElement innerJsonElement = json.getAsJsonObject().get("Data");
        RESULT deserialize;
        try {
            deserialize = deserialize(GSON, json/*innerJsonElement*/, typeOfT, context);
        } catch (JsonParseException pE) {
            throw new ApiRuntimeException(121
                    , ApiConstants.JSON_PARSING_EXCEPTION);
        }
        return deserialize;
    }

    /**
     * Api response Deserializer which identifies if their is any error in
     * api response result parsing then throw the JsonParseException
     *
     * @param gson    GSON Serializer
     * @param json    Response JSON
     * @param typeOfT TYPE of result
     * @return Parsed RESULT
     */
    public abstract RESULT deserialize(Gson gson, JsonElement json, Type typeOfT
            , JsonDeserializationContext context) throws JsonParseException;
}
