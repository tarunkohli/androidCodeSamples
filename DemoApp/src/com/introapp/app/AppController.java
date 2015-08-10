package com.introapp.app;


import android.app.Activity;
import android.content.Context;

import com.introapp.activities.SplashScreen;

/**
 * @author Sachit
 * 
 */
public class AppController {
	/** AppController self instance */
	private static AppController sSelf = null;
	/** UIController variable to handle UI/Screen/Activity navigation */
	private UiController mUiController = null;

	/**
	 * Private constructor
	 * 
	 * @param context
	 */
	private AppController(Context context) {
		mUiController = new UiController();

	}

	/**
	 * Method to get app controller's singleton instance
	 * 
	 * @param context
	 * @return
	 */
	public static AppController getInstance(Context context) {
		if (sSelf == null) {
			sSelf = new AppController(context);
		}
		return sSelf;
	}

	/**
	 * Method to get app controller's singleton instance
	 *
	 * @param
	 * @return
	 */
	public static AppController getInstance() {
		if (sSelf == null) {
			sSelf = new AppController(SplashScreen.getSelf());
		}
		return sSelf;
	}

	/**
	 * Method to handle UI events
	 * 
	 * @param fromActivity
	 * @param toActivityId
	 */
	public static void handleEvent(Activity fromActivity, byte toActivityId) {
		handleEvent(fromActivity, toActivityId, null);
	}

	/**
	 * Method to handle UI events
	 * 
	 * @param fromActivity
	 * @param toActivityId
	 * @param dataToSend
	 */
	public static void handleEvent(Activity fromActivity, byte toActivityId,
			Object dataToSend) {
		AppController.getInstance().mUiController.handleEvent(fromActivity,
				toActivityId, dataToSend);
	}

	/**
	 * Method to start an activity for result
	 * 
	 * @param fromActivity
	 * @param toActivityId
	 * @param requestCode
	 * @param dataToSend
	 */
	public static void handleEventForResult(Activity fromActivity,
			byte toActivityId, int requestCode, Object dataToSend) {
		AppController.getInstance().mUiController.handleEventForResult(
				fromActivity, toActivityId, requestCode, dataToSend);

	}

}
