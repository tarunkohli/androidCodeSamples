package com.vikasgoyal.quovantis.moduleapi.core;


import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.lang.reflect.Type;

public final class ServiceBuilder<SERVICE> {
    Gson mDeserializer;
    Class<SERVICE> mClazz;
    boolean mIsAccessTokenRequired;
    boolean mCanCache;
    String mBaseUrl;


    /**
     * Service class type which need to be register for api call
     *
     * @param pClazz Class Type
     */
    public ServiceBuilder<SERVICE> addClass(Class<SERVICE> pClazz) {
        mClazz = pClazz;
        return this;
    }

    /**
     * Configures Gson for custom deserialization. This method combines the
     * registration of a {@link JsonDeserializer}.
     * all the required interfaces for custom serialization with Gson. If a type adapter was
     * previously registered for the specified {@code type}, it is overwritten.
     * <p/>
     *
     * @param type                 the type definition for the type adapter being registered
     * @param pDeserializer        Add Json deserializer for providing Json data mapping in your custom deserializer
     * @param pSerializeStrategy   To avoid any field on on Serialization provide exclusion strategy here
     * @param pDeserializeStrategy To avoid any field on on Deserialization provide exclusion strategy here
     */
    public <RESULT> ServiceBuilder<SERVICE> addJsonMapper(Type type, ApiDeserializer<RESULT> pDeserializer
            , ExclusionStrategy pSerializeStrategy, ExclusionStrategy pDeserializeStrategy) {
        GsonBuilder builder = new GsonBuilder()
                .serializeNulls();
        if (pDeserializer != null) {
            builder.registerTypeAdapter(type, pDeserializer);
        }
        if (null != pSerializeStrategy) {
            builder.addSerializationExclusionStrategy(pSerializeStrategy);
        }
        if (null != pDeserializeStrategy) {
            builder.addDeserializationExclusionStrategy(pDeserializeStrategy);
        }
        mDeserializer = builder.create();
        return this;
    }

    /**
     * Provide Access token for api call authenticate
     *
     * @param pIsAccessTokenRequired flag if access token required on api call
     */
    public ServiceBuilder<SERVICE> addAccessToken(boolean pIsAccessTokenRequired) {
        mIsAccessTokenRequired = pIsAccessTokenRequired;
        return this;
    }


    /**
     * If want to change api default baseUrl then call this method
     *
     * @param pBaseUrl base url required to make api call
     */
    public ServiceBuilder<SERVICE> addBaseUrl(String pBaseUrl) {
        mBaseUrl = pBaseUrl;
        return this;
    }

    /**
     * If Api call can be cached and if it is already available in cached data then get response from their
     *
     * @param pCanCache true if it can cached else false
     */
    public ServiceBuilder<SERVICE> canCache(boolean pCanCache) {
        mCanCache = pCanCache;
        return this;
    }

    /**
     * Build Service class for provided information
     *
     * @return SERVICE reference
     */
    public SERVICE build() {
        if (null == mClazz) {
            throw new IllegalArgumentException("Class type can't be null for building Service");
        }
        return ApiManager.getInstance().getService(this);
    }
}
