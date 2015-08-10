package com.introapp.library.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class IOUtil {

	// Read the input stream to String
	public static String inputStreamToString(InputStream inputStream)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		Reader reader = new InputStreamReader(inputStream,
				NetworkConstant.TEXT_ENCODING);
		char[] buf = new char[1024];
		int len;
		try {
			while ((len = reader.read(buf)) > 0) {
				buffer.append(buf, 0, len);
			}
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// Log.error("Error closing stream", e);
			}
		}
		return buffer.toString().trim();
	}

	public static String getUrlRequest(String session, String apiUrl) {
		return new StringBuffer(NetworkConstant.BASE_URL).append(session)
				.append(apiUrl).toString();
	}

	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException ioe) {
				// Log.error("Error closing stream", ioe);
			}
		}
	}

}
