package com.qtsachit.helper.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description- This class will provide the helper method for toast utilities
 * <p>
 * Additional notes-
 */
public class ToastUtility {


    private SnackBarAction mSnackBarAction;

    /**
     * This method will show the snack bar with an action
     *
     * @param context context of the activity
     * @param message message that is to be show on the snackbar
     */
    public void snackBarWithAction(Context context, String message,
                                   String actionMessage, int snackBarBackgroundColor,
                                   int snackBarTextColor, int snackBarActionTextColor,
                                   SnackBarAction action) {
        if (context == null)
            return;

        Activity activity = (Activity) context;
        mSnackBarAction = action;
        final Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionMessage, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSnackBarAction.OnActionClicked();
                    }
                });
        View v = snack.getView();
        v.setBackgroundColor(context.getResources().getColor(snackBarBackgroundColor));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(snackBarTextColor);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).
                setTextColor(context.getResources().getColor(snackBarActionTextColor));
        snack.show();
    }

    /**
     * This method will show the snack bar with long time
     *
     * @param context context of the activity
     * @param message message that is to be show on the snackbar
     */
    public static void snackBarLongWithoutAction(Context context, String message,
                                                 int snackBarBackgroundColor,
                                                 int snackBarTextColor, int snackBarActionTextColor) {
        if (context == null)
            return;

        Activity activity = (Activity) context;
        final Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View v = snack.getView();
        v.setBackgroundColor(context.getResources().getColor(snackBarBackgroundColor));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(snackBarTextColor);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).
                setTextColor(context.getResources().getColor(snackBarActionTextColor));
        snack.show();
    }

    /**
     * This method will show the snack bar with short time
     *
     * @param context context of the activity
     * @param message message that is to be show on the snackbar
     */
    public static void snackBarShortWithoutAction(Context context, String message,
                                                  int snackBarBackgroundColor,
                                                  int snackBarTextColor, int snackBarActionTextColor) {
        if (context == null)
            return;

        Activity activity = (Activity) context;
        final Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View v = snack.getView();
        v.setBackgroundColor(context.getResources().getColor(snackBarBackgroundColor));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(snackBarTextColor);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).
                setTextColor(context.getResources().getColor(snackBarActionTextColor));
        snack.show();
    }


    /**
     * This method will show toast on screen with custom message with long time
     *
     * @param context context of the activity
     * @param message message that is to be show while showing the toast
     */
    public static void showLongToastMessage(Context context, String message) {
        if (context == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * This method will show toast on screen with custom message with short time
     *
     * @param context context of the activity
     * @param message message that is to be show while showing the toast
     */
    public static void showShortToastMessage(Context context, String message) {
        if (context == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method will remove the current showing toast message
     *
     * @param toast the object of the active toast
     */
    public static void removeToastMessage(Toast toast) {
        if (toast != null) {
            toast.cancel();
        }
    }


    public interface SnackBarAction {

        public void OnActionClicked();

    }


}
