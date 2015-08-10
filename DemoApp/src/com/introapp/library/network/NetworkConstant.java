package com.introapp.library.network;

/**
 * @author Sachit Interface having base and API urls
 * 
 */
public class NetworkConstant {

	/** base URL to access the services */
	public static String BASE_URL = "http://192.168.0.106:8080/ServiceProject/v1/services";

	// for production
//	 public static final String BASE_URL = "http://app.cruun.com/service";

	/** base URL for access server to get data */

	public static String DEFAULT_URL = "http://192.168.0.106:8080/ServiceProject/v1/";

	// for production
//	 public static final String DEFAULT_URL = "http://app.cruun.com/";

	/**
	 * Type of encoding used to encode the code
	 */
	public static String TEXT_ENCODING = "UTF8";
	/**
	 * Type of the content
	 */
	public static String CONTENT_TYPE = "application/json";
}
