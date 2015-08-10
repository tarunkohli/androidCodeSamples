package com.introapp.library.util;

import java.util.Date;

import android.content.Context;

public class ConsoleLogProvider extends Log {

	public static void install() {
		Log.initialize(new ConsoleLogProvider());
	}

	private ConsoleLogProvider() {
	}

	protected void logInfo(Context context, Object msg) {
		if (msg == null)
			return;
		System.out.print(new Date());
		System.out.println(" STIGGLE INFO ===> ");
		System.out.println(msg.toString());
	}

	protected void logDebug(Context context, Object msg) {
		if (msg == null)
			return;
		System.out.print(new Date());
		System.out.println(" STIGGLE DEBUG ===> ");
		System.out.println(msg.toString());
	}

	protected void logError(Context context, Object msg, Throwable t) {
		if (msg == null && t == null)
			return;
		System.err.print(new Date());
		System.err.println(" STIGGLE ERROR ===> ");
		if (msg != null) {
			System.err.println(msg.toString());
		}
		if (t != null) {
			t.printStackTrace();
		}
	}

	/** */
	@Override
	protected void logWarning(Context context, Object msg) {
		if (msg == null)
			return;
		System.out.print(new Date());
		System.out.println(" STIGGLE warning ===> ");
		System.out.println(msg.toString());
	}

}
