package com.vikasgoyal.quovantis.moduleapi.core;


import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.vikasgoyal.quovantis.moduleapi.logs.ILogger;
import com.vikasgoyal.quovantis.moduleapi.logs.LoggerFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class ApiUtils {

    public static final ILogger LOGGER = LoggerFactory.getLogger();
    public final static String TAG = "ApiUtils";

    /**
     * Parse Api Response to Api result on success
     *
     * @param response Response which need to be parse in result object
     * @param <RESULT> Result object type
     * @return RESULT type object
     */
    public static <RESULT> RESULT parseApiResult(retrofit2.Response<RESULT> response) {
        if (null == response) {
            throw new IllegalArgumentException("Response can't be null");
        }

        return response.body();
    }

    /**
     * Parse Api Response on failure of api call
     *
     * @param response Response which need to be parse in ApiException Object
     * @return ApiException
     */
    public static ApiException parseApiError(retrofit2.Response response) {
        if (null == response) {
            throw new IllegalArgumentException("Response can't be null");
        }
        int code = response.code();

        //handle error code if their is any error
        String message = response.message();
        message = parseApiErrorCode(code, message);
        return new ApiException(code, message);
    }

    public static String parseApiErrorCode(int pCode, String pMessage) {
        switch (pCode) {
            case HttpStatusCode.NOT_FOUND:
                pMessage = "The End Point you are looking for is not available";
                break;
            case HttpStatusCode.BAD_REQUEST:
                pMessage = "Please try again";
                break;
            case HttpStatusCode.FORBIDDEN:
                //On Authorization failed need to show login again screen
                /*List<AuthFailedListener> uiListeners = EventManager.getInstance()
                        .getUiListeners(AuthFailedListener.class);
                if (uiListeners != null && !uiListeners.isEmpty()) {
                    uiListeners.get(0).onAuthenticationFailed(new ApiException(HttpStatusCode.FORBIDDEN));
                }*/
                pMessage = "";//"Authentication Token is expired";
                break;
        }
        return pMessage;
    }

    /**
     * Parse Api Response on failure of api call
     *
     * @param response Response which need to be parse in ApiException Object
     * @return ApiException
     */
    public static ApiException parseApiError(String response) throws ApiException {
        if (TextUtils.isEmpty(response)) {
            throw new IllegalArgumentException("Response can't be null");
        }
        try {
            //Create log file
//            printLogsInFile(response);
            JSONObject responseObject;
            responseObject = new JSONObject(response);

            String status = responseObject.optString("Status");
            int errorCode = responseObject.optInt("ErrorCode");
            String errorMsg = responseObject.optString("ErrorMessage");
            if (!TextUtils.isEmpty(status) && ApiConstants.NOK.equalsIgnoreCase(status)) {
                LOGGER.log("Api Execution Response", response);
                Log.i(TAG + "Api Error", String.format("Error Code : %s \t ResponseBody : %s", errorCode, errorMsg));
                return new ApiException(errorCode, errorMsg);
            }
        } catch (JSONException pE) {
            LOGGER.log("Api Execution Response", response);
            Log.i(TAG + "Api Error", "Response : " + response);
            return null;
        }
        return null;
    }

    private static void printLogsInFile(String response) {
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.txt");
        try {
            boolean newFile = false;
            if (!file.exists()) {
                newFile = file.createNewFile();
            }
            if (newFile) {
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.write(response);
                fileWriter.close();
            }
        } catch (IOException pE) {
            pE.printStackTrace();
        }
    }

    /**
     * Parse Api Response on failure of api call
     *
     * @param exception Exception which need to be parse in ApiException Object
     * @return ApiException
     */
    public static ApiException parseApiException(Throwable exception) {
        if (null == exception) {
            throw new IllegalArgumentException("Exception can't be null");
        }
        if (exception instanceof SocketTimeoutException) {
            return new ApiException(HttpStatusCode.TIMEOUT_EXCEPTION, exception.getMessage());
        }
        LOGGER.log("Api Execution Error", exception.getMessage());
        return new ApiException(HttpStatusCode.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    /**
     * Print request body on logs use this method
     *
     * @param pUrl        Api request Url
     * @param pApiRequest Request
     * @throws IOException
     */
    public static Request printRequestBody(String pUrl, Request pApiRequest) throws IOException {
        if (null != pApiRequest) {
            String authorization = pApiRequest.header("Authorization");
            Log.i(TAG, String.format("URL : %s%s", pUrl, (null != authorization ? ":\nToken : " + authorization : "")));
            String requestBody = "";
            if (null != pApiRequest.body()) {
                Buffer buffer = new Buffer();
                pApiRequest.body().writeTo(buffer);
                requestBody = buffer.readUtf8();
            }
            Log.i(TAG, "/ RequestBody : " + requestBody);
        }
        return pApiRequest;
    }


    /**
     * Print request body on logs use this method
     *
     * @param pResponse     Request
     * @param pResponseTime Api execution time
     * @throws IOException Exception in execution  of Api
     */
    public static Response handleApiResponse(Response pResponse, long pResponseTime) throws IOException {
        if (null != pResponse) {


            //print response time logs
            printResponseTime("Api Execution Time", pResponseTime);
            if (null != pResponse.body()) {
                String bodyString = pResponse.body().string();
                Log.i(TAG, "/ ResponseBody : " + bodyString);

                //Check whether their is response body is already null or not
                if (TextUtils.isEmpty(bodyString)) {
                    throw new IllegalArgumentException("Response can't be null");
                }
                // body can be read only once so in case of printing log we are creating response again
                return pResponse.newBuilder()
                        .body(ResponseBody.create(pResponse.body().contentType(), bodyString))
                        .build();
            }

        }
        return pResponse;
    }

    /**
     * TO print api execution time on console provide pResponse time in nano
     *
     * @param msg           Where from time is printing
     * @param pResponseTime response time in nano seconds
     */
    public static void printResponseTime(String msg, long pResponseTime) {
        String execTime = String.valueOf(pResponseTime);
        String millisTime = String.valueOf(pResponseTime / 1000000L);
        Log.i(TAG, String.format("%s : %s nano seconds or %s millis", msg, execTime, millisTime));
    }
}
