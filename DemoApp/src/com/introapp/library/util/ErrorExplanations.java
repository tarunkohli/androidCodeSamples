package com.introapp.library.util;

import java.util.HashMap;

public class ErrorExplanations {
	private static final HashMap<String, String> errors = new HashMap<String, String>();

	public static String get(String code) {
		return errors.get(code);
	}

	static {
//		errors.put("E_USER_DOES_NOT_EXIST",
//				String.valueOf(R.string.E_USER_DOES_NOT_EXIST));
	

	}
}
