package com.introapp.network;

import java.io.IOException;

import com.introapp.library.network.HttpBaseConnectionProvider;

public class HttpConnectionProvider extends HttpBaseConnectionProvider {
	private String method;
	private boolean checkStaus;

	@Override
	public String doGet(String url, ParameterMap arguments) throws IOException {
		AndroidHttpClient httpClient = new AndroidHttpClient();
		httpClient.setMaxRetries(1);
		
		HttpResponse response = httpClient.get(url, arguments);
		if (response != null) {
			return new String(response.getBody());

		}
		return "";
	}

	@Override
	public String doPost(String url, ParameterMap properties,
			String contentType, byte[] data, Object obj) throws IOException {
		AndroidHttpClient httpClient = new AndroidHttpClient();
		httpClient.setMaxRetries(1);
		HttpResponse response = httpClient.post(url, properties, contentType,
				data, obj);

		if (response != null) {
			return new String(response.getBody());
		}
		return "";
	}

	@Override
	public void closeConnection() {

	}

	/** */
	@Override
	public String getHttpMethod() {
		return this.method;
	}

	/** */
	@Override
	public void setHttpMethod(String method) {
		this.method = method;

	}

	/** */
	@Override
	public void setCheckStaus(boolean staus) {
		this.checkStaus = staus;
	}

	/** */
	@Override
	public boolean getCheckStatus() {
		return this.checkStaus;
	}

}
