package com.introapp.network;

//import com.trafficlaw.network.httpclient.android.DoHttpRequestTask;

/**
 * An HTTP client that completes all requests asynchronously using
 * {@link DoHttpRequestTask}. All methods take a callback argument which is
 * passed to the task and whose methods are invoked when the request completes
 * or throws on exception.
 * 
 * @author David M. Chandler
 */
public class HttpClient extends AbstractHttpClient {

	static int[] fib = new int[20];
	static {
		// Compute Fibonacci series for backoff
		for (int i = 0; i < 20; i++) {
			fib[i] = i < 2 ? i : fib[i - 2] + fib[i - 1];
		}
	}

	// Configurable default
	private int maxRetries = 3;

	/*
	 * The factory that will be used to obtain an async wrapper for the request.
	 * Usually set in a subclass that provides a platform-specific async wrapper
	 * such as a new thread or Android AsyncTask.
	 */
	// protected final AsyncRequestExecutorFactory execFactory;

	/**
	 * Constructs a new client with factory, baseUrl, and custom
	 * {@link RequestHandler}.
	 * 
	 * @param factory
	 * @param baseUrl
	 * @param handler
	 */
	public HttpClient(String baseUrl) {
		super(baseUrl);
	}

	/**
	 * Constructs a new client with factory, baseUrl, and custom
	 * {@link RequestHandler}.
	 * 
	 * @param factory
	 * @param baseUrl
	 * @param handler
	 */
	public HttpClient(String baseUrl, RequestHandler handler) {
		super(baseUrl, handler);
	}

	/**
	 * Execute a HEAD request and invoke the callback on completion. The
	 * supplied parameters are URL encoded and sent as the query string.
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public HttpResponse head(String path, ParameterMap params) {
		HttpRequest req = new HttpHead(path, params);
		return executeAsync(req);
	}

	/**
	 * Execute a GET request and invoke the callback on completion. The supplied
	 * parameters are URL encoded and sent as the query string.
	 * 
	 * @param path
	 * @param params
	 * @param callback
	 */
	public HttpResponse get(String path, ParameterMap params) {
		HttpRequest req = new HttpGet(path, params);
		return executeAsync(req);
	}

	/**
	 * Execute a POST request with parameter map and invoke the callback on
	 * completion.
	 * 
	 * @param path
	 * @param params
	 * @param callback
	 */
	public HttpResponse post(String path, ParameterMap params) {
		HttpRequest req = new HttpPost(path, params);

		return executeAsync(req);
	}

	public HttpResponse post(String path, ParameterMap params,
			String contentType, byte[] data,Object obj) {
		HttpRequest req = new HttpPost(path, params, contentType, data,obj);
		return executeAsync(req);
	}

	/**
	 * Execute a POST request with a chunk of data and invoke the callback on
	 * completion.
	 * 
	 * To include name-value pairs in the query string, add them to the path
	 * argument or use the constructor in {@link HttpPost}. This is not a common
	 * use case, so it is not included here.
	 * 
	 * @param path
	 * @param contentType
	 * @param data
	 * @param callback
	 */
	public HttpResponse post(String path, String contentType, byte[] data,Object obj) {
		HttpPost req = new HttpPost(path, null, contentType, data,obj);
		return executeAsync(req);
	}

	/**
	 * Execute a PUT request with the supplied content and invoke the callback
	 * on completion.
	 * 
	 * To include name-value pairs in the query string, add them to the path
	 * argument or use the constructor in {@link HttpPut}. This is not a common
	 * use case, so it is not included here.
	 * 
	 * @param path
	 * @param contentType
	 * @param data
	 * @param callback
	 */
	public HttpResponse put(String path, String contentType, byte[] data) {
		HttpRequest req = new HttpPut(path, null, contentType, data);
		return executeAsync(req);
	}

	/**
	 * Execute a DELETE request and invoke the callback on completion. The
	 * supplied parameters are URL encoded and sent as the query string.
	 * 
	 * @param path
	 * @param params
	 * @param callback
	 * @return
	 */
	public HttpResponse delete(String path, ParameterMap params) {
		HttpDelete req = new HttpDelete(path, params);
		return executeAsync(req);
	}

	/**
	 * Execute an {@link HttpRequest} asynchronously. Uses a factory to obtain a
	 * suitable async wrapper in order to decouple from Android.
	 * 
	 * @param httpRequest
	 * @param callback
	 */
	protected HttpResponse executeAsync(HttpRequest httpRequest) {
		/*
		 * AsyncRequestExecutor executor = execFactory.getAsyncRequestExecutor(
		 * this, callback); executor.execute(httpRequest);
		 */
		try {

			return tryMany(httpRequest);
		} catch (HttpRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Tries several times until successful or maxRetries exhausted. Must throw
	 * exception in order for the async process to forward it to the callback's
	 * onError method.
	 * 
	 * @param httpRequest
	 * @return Response object, may be null
	 * @throws HttpRequestException
	 */
	public HttpResponse tryMany(HttpRequest httpRequest)
			throws HttpRequestException {

		int numTries = 0;
		long startTime = System.currentTimeMillis();
		HttpResponse res = null;
		while (numTries < maxRetries) {
			try {
				setConnectionTimeout(getNextTimeout(numTries));
				if (requestLogger.isLoggingEnabled()) {
					requestLogger.log((numTries + 1) + "of" + maxRetries
							+ ", trying " + httpRequest.getPath());
				}
				startTime = System.currentTimeMillis();
				res = doHttpMethod(httpRequest.getPath(),
						httpRequest.getHttpMethod(),
						httpRequest.getContentType(), httpRequest.getContent());
				if (res != null) {
					return res;
				}
			} catch (HttpRequestException e) {
				if (isTimeoutException(e, startTime)
						&& numTries < (maxRetries - 1)) {
					// Fall through loop, retry
					// On last attempt, throw the exception regardless
				} else {
					boolean isRecoverable = requestHandler.onError(e);
					if (isRecoverable && numTries < (maxRetries - 1)) {
						try {
							// Wait a while and fall through loop to try again
							Thread.sleep(connectionTimeout);
						} catch (InterruptedException ie) {
							// App stopping, perhaps? No point in further
							// retries
							throw e;
						}
					} else {
						// Not recoverable or last attempt, time to bail
						throw e;
					}
				}
			}
			numTries++;
		}
		return null;
	}

	/**
	 * Implements exponential backoff using the Fibonacci series, which has the
	 * effect of backing off with a multiplier of ~1.618 (the golden mean)
	 * instead of 2, which is rather boring.
	 * 
	 * @param numTries
	 *            Current number of attempts completed
	 * @return Connection timeout in ms for next attempt
	 */
	protected int getNextTimeout(int numTries) {
		// For n=0,1,2,3 returns 1000,2000,3000,5000
		return 5000 * fib[numTries + 2];
	}

	/**
	 * Set maximum number of retries to attempt, capped at 18. On the 18th
	 * retry, the connection timeout will be 4,181 sec = 1 hr 9 min.
	 * 
	 * @param maxRetries
	 */
	public void setMaxRetries(int maxRetries) {
		if (maxRetries < 1 || maxRetries > 18) {
			throw new IllegalArgumentException(
					"Maximum retries must be between 1 and 18");
		}
		this.maxRetries = maxRetries;
	}

}