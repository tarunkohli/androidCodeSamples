package com.qtsachit.helper.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description- This class will provide the helper method for notifications utilities
 * <p>
 * Additional notes-
 */

public class NotificationUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private NotificationUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * ViewUtility function to display a notification from an activity.
     *
     * @param message        The text the notification holds.
     * @param title          The title of the notification
     * @param targetActivity The activity to start when the notification is clicked.
     * @param context        context of the activity
     * @param icon           that is need to show when notification occurs
     */
    public static void displayNotification(String message, String title,
                                           Class<?> targetActivity, Context context, int icon) {

        Intent intent = new Intent(context, targetActivity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pendingIntent);

        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
