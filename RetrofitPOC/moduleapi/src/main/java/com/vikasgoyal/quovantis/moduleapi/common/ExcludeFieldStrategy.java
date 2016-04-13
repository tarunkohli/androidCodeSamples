package com.vikasgoyal.quovantis.moduleapi.common;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * To exclude a field in Gson
 */
public class ExcludeFieldStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
