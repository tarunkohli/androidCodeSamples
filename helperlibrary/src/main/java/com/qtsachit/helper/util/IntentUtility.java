package com.qtsachit.helper.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.qtsachit.helper.R;

import java.util.List;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description- This class will provide all the helper method for using intents like in case of
 * call, switching to another activity
 * <p>
 * Additional notes-
 */
public class IntentUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private IntentUtility() {
        throw new Error("Do not need instantiate!");
    }


    public static Intent getOpenURLIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static void openURL(Context context, String url) {
        Intent intent = getOpenURLIntent(url);
        try {
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.intent_open_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_open_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getCallPhoneIntent(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        return callIntent;
    }

    public static void callPhone(Context context, String number) {
        Intent intent = getCallPhoneIntent(number);
        try {
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.intent_phone_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_phone_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getSendSMSIntent(String number, String body) {
        Intent callIntent = new Intent(Intent.ACTION_SENDTO);
        callIntent.setData(Uri.parse("sms:" + number));
        callIntent.putExtra("sms_body", body);
        return callIntent;
    }

    public static void sendSMS(Context context, String number, String body) {
        Intent intent = getSendSMSIntent(number, body);

        try {
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.intent_sms_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_sms_error, Toast.LENGTH_SHORT).show();
        }
    }


    public static Intent getEmailIntent(String address, String subject, CharSequence content) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, content);
        return mailIntent;
    }

    public static void sendEmail(Context context, String address, String subject, CharSequence content) {
        Intent mailIntent = getEmailIntent(address, subject, content);

        try {
            Intent chooser = Intent.createChooser(mailIntent, context.getString(R.string.intent_email_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_email_error, Toast.LENGTH_SHORT).show();
        }
    }


    public static Intent getMapCoordinatesIntent(double latitude, double longitude, String markerTitle) {
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;
        if (markerTitle != null && markerTitle.length() > 0) {
            uri += "(" + markerTitle + ")";
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    }

    public static void showMapCoordinates(Context context, double latitude, double longitude, String markerTitle) {
        Intent intent = getMapCoordinatesIntent(latitude, longitude, markerTitle);
        try {
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.intent_map_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_map_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getMapRouteIntent(double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo) {
        Uri uri = Uri.parse("http://maps.google.com/maps?saddr=" + latitudeFrom + "," + longitudeFrom + "&daddr=" + latitudeTo + "," + longitudeTo + "");
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void showMapRoute(Context context, double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo) {
        Intent intent = getMapRouteIntent(latitudeFrom, longitudeFrom, latitudeTo, longitudeTo);
        try {
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.intent_map_choose));
            context.startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.intent_map_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static void openDialer(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    public static List<ResolveInfo> getResolveInfoList(PackageManager pm) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return pm.queryIntentActivities(mainIntent, 0);
    }
}
