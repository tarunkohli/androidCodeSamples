package com.introapp.network;

import java.net.HttpURLConnection;

import android.os.AsyncTask;
import android.os.Build;

/**
 * HTTP client for Android providing both synchronous (blocking) and
 * asynchronous interfaces so it can be used on or off the UI thread.
 * 
 * <p>
 * Sample usage:
 * </p>
 * 
 * <p>
 * Synchronous (for use off the UI thread in an {@link AsyncTask} or
 * {@link Runnable})
 * </p>
 * 
 * <pre>
 * AndroidHttpClient httpClient = new AndroidHttpClient(&quot;http://www.google.com&quot;);
 * ParameterMap params = httpClient.newParams().add(&quot;q&quot;, &quot;GOOG&quot;);
 * HttpResponse httpResponse = httpClient.get(&quot;/finance&quot;, params);
 * // System.out.println(httpResponse.getBodyAsString());
 * </pre>
 * 
 * <p>
 * Asynchronous (can be used anywhere, automatically wraps in an
 * {@link AsyncTask})
 * </p>
 * 
 * <pre>
 * AndroidHttpClient httpClient = new AndroidHttpClient(&quot;http://www.google.com&quot;);
 * ParameterMap params = httpClient.newParams().add(&quot;q&quot;, &quot;GOOG&quot;);
 * httpClient.setMaxRetries(3);
 * httpClient.get(&quot;/finance&quot;, params, new AsyncCallback() {
 * 	public void onComplete(HttpResponse httpResponse) {
 * 		// System.out.println(httpResponse.getBodyAsString());
 * 	}
 * 
 * 	public void onError(Exception e) {
 * 		e.printStackTrace();
 * 	}
 * });
 * </pre>
 * 
 * @author David M. Chandler
 */
public class AndroidHttpClient extends HttpClient {

	static {
		disableConnectionReuseIfNecessary();
	}

	/**
	 * Constructs a new client with empty baseUrl. When used this way, the path
	 * passed to a request method must be the complete URL.
	 */
	public AndroidHttpClient() {
		this("");
	}

	/**
	 * Constructs a new client using the default {@link RequestHandler} and
	 * {@link RequestLogger}.
	 */
	public AndroidHttpClient(String baseUrl) {
		super(baseUrl);
	}

	/**
	 * Constructs a client with baseUrl and custom {@link RequestHandler}.
	 * 
	 * @param baseUrl
	 * @param requestHandler
	 */
	public AndroidHttpClient(String baseUrl, RequestHandler requestHandler) {
		super(baseUrl, requestHandler);
	}

	/**
	 * Work around bug in {@link HttpURLConnection} on older versions of
	 * Android.
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 */
	private static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

}
