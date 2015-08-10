package com.introapp.library.network;

import java.io.IOException;

import com.introapp.network.ParameterMap;



public abstract class HttpBaseConnectionProvider {

	/*
	 * private static HttpBaseConnectionProvider instance;
	 * 
	 * public static final HttpBaseConnectionProvider getInstance() { if
	 * (instance == null) { throw new IllegalStateException("Not initialized");
	 * } return instance; }
	 * 
	 * public static final void initialize(HttpBaseConnectionProvider provider)
	 * { instance = provider; }
	 */

	public abstract String doGet(String url, ParameterMap arguments)
			throws IOException;

	public abstract String doPost(String url, ParameterMap properties,
			String contentType, byte[] data,Object obj) throws IOException;

	public abstract String getHttpMethod();

	public abstract void setCheckStaus(boolean staus);

	public abstract boolean getCheckStatus();

	public abstract void setHttpMethod(String method);

	public abstract void closeConnection();
}
