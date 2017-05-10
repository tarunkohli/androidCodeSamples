package com.qtsachit.helper.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description- This class will provide the helper method for utilities.
 * <p>
 * Additional notes-
 */
public class ViewUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private ViewUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * Gives the device independent constant which can be used for scaling images, manipulating view
     * sizes and changing dimension and display pixels etc.
     * **
     */
    public static float getDensityMultiplier(Context ctx) {
        return ctx.getResources().getDisplayMetrics().density;
    }

    /**
     * Gets the name of the application that has been defined in AndroidManifest.xml
     *
     * @throws android.content.pm.PackageManager.NameNotFoundException **
     */
    public static String getApplicationName(Context ctx) throws NameNotFoundException {

        if (ctx == null) {
            throw new NullPointerException("Context cannot be null");
        }

        final PackageManager packageMgr = ctx.getPackageManager();
        ApplicationInfo appInfo = null;

        try {
            appInfo = packageMgr.getApplicationInfo(ctx.getPackageName(), PackageManager.SIGNATURE_MATCH);
        } catch (final NameNotFoundException e) {
            throw new NameNotFoundException(e.getMessage());
        }

        final String applicationName = (String) (appInfo != null ? packageMgr.getApplicationLabel(appInfo) : "UNKNOWN");

        return applicationName;
    }


    /**
     * This method will tell that your mobile airplane mode is ON or not
     *
     * @param context context of the activity
     * @return true if airplane mode is ON else return false
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }


    /**
     * This method will convert dip to px, it will based on the screen resolution
     *
     * @param context context of an activity
     * @param dpValue dp value
     * @return dp value
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * This method will convert px to dip, it will based on the screen resolution
     *
     * @param context context of an activity
     * @param pxValue px value
     * @return px value
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * This method will convert px to sp, it will based on the screen resolution
     *
     * @param context context of an activity
     * @param pxValue px value
     * @return px value
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * This method will convert sp to px, it will based on the screen resolution
     *
     * @param context context of an activity
     * @param spValue sp value
     * @return px value
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * This method will return the width of the dialog
     *
     * @param activity Activity
     * @return width of dialog
     */
    public static int getDialogW(Activity activity) {
        DisplayMetrics dm;
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        return w;
    }

    /**
     * This method will return the width of the screen
     *
     * @param activity Activity
     * @return width of the screen
     */
    public static int getScreenW(Activity activity) {
        DisplayMetrics dm;
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * This method will return the height of the screen
     *
     * @param activity Activity
     * @return height of the screen
     */
    public static int getScreenH(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }

    /**
     * Toggle keyboard If the keyboard is visible,then hidden it,if it's
     * invisible,then show it
     *
     * @param context context of an activity
     */
    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

}