package com.qtsachit.helper.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * The class is Created by Sachit on 03/February/2017
 * <p>
 * Description-
 * <p>
 * Additional notes-
 */
public class ShareUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private ShareUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * This method will show the apps install in phone that can share the image.
     *
     * @param context context of an activity
     * @param uri     uri of the image
     * @param title   title of the sharer
     */
    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    /**
     * This method will show the apps install in phone that can share
     *
     * @param context   context of an activity
     * @param extraText text to be appended
     */
    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, "Choose app to share"));
    }
}
