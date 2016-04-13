package com.vikasgoyal.quovantis.moduleapi.core.multipart;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.vikasgoyal.quovantis.moduleapi.common.UrlBuilder;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultipartRequest<RESULT> {
    /**
     * Multi part Entity
     */
    private Pair<String, MultipartEntity> mEntity;
    /**
     * Headers map
     */
    private HashMap<String, String> mHeaders;

    /**
     * Api end point url
     */
    private String mUrl;

    private Class<RESULT> mClazz;

    @NonNull
    public Pair<String, MultipartEntity> getEntity() {
        return mEntity;
    }

    public MultipartRequest(Class<RESULT> pClazz) {
        mClazz = pClazz;
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    @NonNull
    public Class<RESULT> getClazz() {
        return mClazz;
    }

    public static class Builder<RESULT> {
        private String mFileName;
        private File mFile;
        private String mEndPoint;
        private final MultipartRequest<RESULT> mRequest;
        /**
         * Query params map
         */
        private HashMap<String, String> mQueryParams;

        public Builder(Class<RESULT> pClass) {
            mQueryParams = new HashMap<>();
            mRequest = new MultipartRequest<>(pClass);
            mRequest.mHeaders = new HashMap<>();
        }

        /**
         * Add File to upload on server
         *
         * @param pName FileName
         * @param pFile File
         */
        public Builder putFile(String pName, File pFile) {
            mFileName = pName;
            mFile = pFile;
            return this;
        }

        /**
         * Add on demand header for request
         *
         * @param pKey   header key
         * @param pValue header value
         */
        public Builder addHeader(String pKey, String pValue) {
            mRequest.mHeaders.put(pKey, pValue);
            return this;
        }

        /**
         * Add End Point
         *
         * @param pEndPoint Api execution end point
         */
        public Builder addEndPoint(String pEndPoint) {
            mEndPoint = pEndPoint;
            return this;
        }

        /**
         * Add on demand Query params for request
         *
         * @param pKey   Query params key
         * @param pValue Query params value
         */
        public Builder addQuery(String pKey, String pValue) {
            mQueryParams.put(pKey, pValue);
            return this;
        }

        /**
         * Build Multipart request to pass it on RestClient
         *
         * @return MultipartRequest
         */
        public MultipartRequest<RESULT> build() {
            String boundary = String.valueOf(UUID.randomUUID());
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE
                    , boundary, Charset.forName("UTF-8"));
            FileBody fileBody = new FileBody(mFile);
            entity.addPart(mFileName, fileBody);
            mRequest.mEntity = new Pair<>(boundary, entity);
            mRequest.mUrl = UrlBuilder
                    .getInstance()
                    .build(mEndPoint, mQueryParams);
            return mRequest;
        }
    }
}
