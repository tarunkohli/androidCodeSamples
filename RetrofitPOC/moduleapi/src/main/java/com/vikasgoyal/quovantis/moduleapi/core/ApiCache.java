package com.vikasgoyal.quovantis.moduleapi.core;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;

/**
 * Api Cache will provide api caching using okHttp Cache
 */
public class ApiCache {
    private final static String TAG = "ApiCache";
    private final Cache mCache;
    public static final String CACHE_FILE_NAME = "ifittr.api.cahche";
    private final static long CACHE_SIZE = 4 * 1024L * 1024L * 1000L;//4MB CACHE SPACE FOR API CALLS

    /**
     * Create new instance
     */
    public ApiCache() {
        File directory = getDirectory();
        mCache = new Cache(directory, CACHE_SIZE);
    }

    private File getDirectory() {
        final File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "UCC" + File.separator);
        if (root.exists()) {
            root.deleteOnExit();
        }

        Log.i(TAG, String.valueOf(root.mkdirs()));
        return new File(root, ApiCache.CACHE_FILE_NAME);
    }

    public Cache getCache() {
        return mCache;
    }

    /**
     * Clear cache when need to download for ne files
     */
    public void clearCache() {
        if (null != mCache) {
            try {
                mCache.evictAll();
            } catch (IOException pE) {
                pE.printStackTrace();
            }
        }
    }
}
