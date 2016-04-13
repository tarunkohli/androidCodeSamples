package com.vikasgoyal.quovantis.moduleapi.common;

import android.text.TextUtils;

import java.util.Map;

public class UrlBuilder {

    private final String mBaseUrl;

    private static UrlBuilder sInstance;

    /**
     * Constructor.
     *
     * @param baseUrl base url of endpoint.
     */
    private UrlBuilder(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    /**
     * Init the BaseUrl is life cycle of application once
     *
     * @param mBaseUrl Base Url for api calling
     */
    public static void init(String mBaseUrl) {
        if (null == sInstance) {
            synchronized (UrlBuilder.class) {
                sInstance = new UrlBuilder(mBaseUrl);
            }
        }
    }

    /**
     * Get SingleTon Instance of this class and before using this you need to call
     * init method
     *
     * @return Class singleton instance
     */
    public synchronized static UrlBuilder getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Before Using this class you need to call init first");
        }
        return sInstance;
    }

    /**
     * Get base url of endpoint.
     *
     * @return base url.
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * Return full resource url by appending given path to base url.
     *
     * @param path resource path.
     * @return full resource url.
     */
    public synchronized String build(String path) {
        if (TextUtils.isEmpty(path)) {
            return mBaseUrl;
        }

        // ensure proper joining with "/"
        if (mBaseUrl.endsWith("/") && path.startsWith("/")) {
            path = path.substring(1);
        } else if (!mBaseUrl.endsWith("/") && !path.startsWith("/")) {
            path = "/" + path;
        }

        return mBaseUrl + path;
    }


    /**
     * Return full resource url by appending given path to base url.
     *
     * @param path        resource path.
     * @param pQueryParam Query Param
     * @return full resource url.
     */
    public synchronized String build(String path, Map<String, String> pQueryParam) {
        if (TextUtils.isEmpty(path)) {
            return mBaseUrl;
        }

        // ensure proper joining with "/"
        if (mBaseUrl.endsWith("/") && path.startsWith("/")) {
            path = path.substring(1);
        } else if (!mBaseUrl.endsWith("/") && !path.startsWith("/")) {
            path = "/" + path;
        }
        String appendedUrl = mBaseUrl + path;
        if (!pQueryParam.isEmpty()) {
            for (String key : pQueryParam.keySet()) {
                appendedUrl = appendedUrl.concat(appendedUrl.contains("?") ? "&" : "?");
                appendedUrl = appendedUrl.concat(key + "=" + pQueryParam.get(key));
            }
        }
        return appendedUrl;
    }
}
