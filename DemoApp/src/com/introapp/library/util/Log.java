package com.introapp.library.util;

import android.content.Context;

public abstract class Log {
	private static Log instance;
	/** if you want logs then set it true **/
	private final static boolean isDebug = false;

	public static void initialize(Log provider) {
		instance = provider;
	}

	private static Log getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Not initialized");
		}
		return instance;
	}

	public static void info(Context context, Object msg) {
		if (isDebug)
			getInstance().logInfo(context, msg);
	}

	public static void debug(Context context, Object msg) {
		if (isDebug)
			getInstance().logDebug(context, msg);
	}

	public static void error(Context context, Object msg) {
		if (isDebug)
			getInstance().logError(context, msg, null);
	}

	public static void error(Context context, Object msg, Throwable t) {
		if (isDebug)
			getInstance().logError(context, msg, t);
	}

	public static void warning(Context context, Object msg) {
		if (isDebug)
			getInstance().logWarning(context, msg);
	}

	protected abstract void logInfo(Context context, Object msg);

	protected abstract void logDebug(Context context, Object msg);

	protected abstract void logError(Context context, Object msg, Throwable t);

	protected abstract void logWarning(Context context, Object msg);
}
