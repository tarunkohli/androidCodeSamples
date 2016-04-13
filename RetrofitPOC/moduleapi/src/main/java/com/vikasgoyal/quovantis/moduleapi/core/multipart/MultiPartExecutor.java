package com.vikasgoyal.quovantis.moduleapi.core.multipart;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * A executor which execute the api call and provide response or error on result
 * On failure of api an error will be pass in response else the result is passed
 */
public class MultiPartExecutor {
    public static final int REQUEST_POOL_SIZE = 5;

    private HashMap<String, Future<?>> mRequestQueueMap = new HashMap<>();
    private ExecutorService mRequestExecutorService = Executors.newFixedThreadPool(REQUEST_POOL_SIZE);

    /**
     * Execute Api Service in background and receive result for that
     * * @param <RESULT> Type of result required in response
     */

    <RESULT> Future execute(String pTag, Runnable multiPartExecutor) {
        Future<?> task = mRequestExecutorService.submit(multiPartExecutor);
        mRequestQueueMap.put(pTag, task);
        return task;
    }

    /**
     * To Cancel the execution of request use this method
     */
    public void cancel(String pTag) {
        if (mRequestQueueMap.containsKey(pTag)) {
            Future future = mRequestQueueMap.get(pTag);
            future.cancel(true);
            mRequestQueueMap.remove(pTag);
        }
    }

}
