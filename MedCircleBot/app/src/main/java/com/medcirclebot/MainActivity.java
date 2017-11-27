package com.medcirclebot;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private static final String DEEP_LINK_URL = "https://abc.com/abc-1";

    private GoogleApiClient mGoogleApiClient;
    private Uri deepLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // [START_EXCLUDE]
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        validateAppCode();
        deepLink = buildDeepLink(Uri.parse(DEEP_LINK_URL), 0, false);
        ((TextView) findViewById(R.id.link_view_send)).setText("https://bot.medcircle.com/s/");
        initGoogleClient();
        boolean autoLaunchDeepLink = false;
        //
        handleIntent(getIntent());

        //whenUserCLickedOnLink(autoLaunchDeepLink);
    }

    private void handleIntent(Intent intent) {
        if(intent!=null){
            Uri appLinkData = intent.getData();
            if(appLinkData!=null)
            startActivity(new Intent(MainActivity.this, ChatActivity.class));
        }
    }

    private void whenUserCLickedOnLink(boolean autoLaunchDeepLink) {
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                            }
                        });
    }

    private void initGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();
    }

    @VisibleForTesting
    public Uri buildDeepLink(@NonNull Uri deepLink, int minVersion, boolean isAd) {
        String appCode = getString(R.string.app_code);

        String packageName = getApplicationContext().getPackageName();
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(appCode + ".app.goo.gl")
                .path("/")
                .appendQueryParameter("link", deepLink.toString())
                .appendQueryParameter("apn", packageName);

        if (isAd) {
            builder.appendQueryParameter("ad", "1");
        }

        if (minVersion > 0) {
            builder.appendQueryParameter("amv", Integer.toString(minVersion));
        }

        return builder.build();
    }

    private void shareDeepLink(String deepLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }

    private void validateAppCode() {
        String appCode = getString(R.string.app_code);
        if (appCode.contains("YOUR_APP_CODE")) {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Configuration")
                    .setMessage("Please set your app code in res/values/strings.xml")
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services Error: " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.button_share)
    protected void onShareLinkClicked() {
        shareDeepLink(deepLink.toString());
    }

    @OnClick(R.id.button_chat)
    protected void onNeedAssitanceButtonClicked() {
        startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }
}
