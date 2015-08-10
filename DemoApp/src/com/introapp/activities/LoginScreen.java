package com.introapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.intro.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.introapp.app.AppController;
import com.introapp.library.util.Constants;
import com.introapp.library.util.EventHandler;
import com.introapp.library.util.Utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

public class LoginScreen extends BaseScreen implements View.OnClickListener, Constants.APPKEYS, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {

    private EditText mUserid;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private HashMap<String, Object> mMap;
    private LoginButton mLoginFacebook;
    private CallbackManager mCallBackManager;
    private FacebookCallback<LoginResult> mCallBack;
    private ProfileTracker mProfileTracker;
    private AccessTokenTracker mAccessTokenTracker;
    private SignInButton mLoginWithGoogle;
    private GoogleApiClient mGooglePlusClient;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private Button mSignOutFromGoogle;
    private Button mRevokeGoogleAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);
        initializeFacbookCallBack();
        initializeGooglelusClient();
        findViewById();
    }

    private void initializeGooglelusClient() {
        mGooglePlusClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);
        return builder.build();
    }

    private void initializeFacbookCallBack() {
        mCallBackManager = CallbackManager.Factory.create();
//        mCallBack   =   new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                AccessToken lToken  =   loginResult.getAccessToken();
//
//
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        };

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                AccessToken lToken = currentAccessToken;
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Profile lProfile = currentProfile;
                if (lProfile != null) {

                    String lName = lProfile.getName();
                    Toast.makeText(LoginScreen.this, lName, Toast.LENGTH_LONG).show();

                }

            }
        };

    }

    /**
     * find id's of all controls used in Activity
     */

    private void findViewById() {

        mUserid = (EditText) findViewById(R.id.userid_edittext);
        mPassword = (EditText) findViewById(R.id.password_edittext);
        mLogin = (Button) findViewById(R.id.login_btn);
        mLoginFacebook = (LoginButton) findViewById(R.id.login_button);
        mLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
        mLoginFacebook.registerCallback(mCallBackManager, mCallBack);
        mLoginWithGoogle = (SignInButton) findViewById(R.id.googlebtn);
        mLoginWithGoogle.setOnClickListener(this);
        mRegister = (Button) findViewById(R.id.register_btn);
        mSignOutFromGoogle  =   (Button) findViewById(R.id.signout_btn);
        mSignOutFromGoogle.setOnClickListener(this);
        mRevokeGoogleAccess =   (Button) findViewById(R.id.revokeaccess_btn);
        mRevokeGoogleAccess.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_btn:
                AppController.handleEvent(LoginScreen.this, Constants.ACTIVITY_STATES.REGISTER, "");
                break;

            case R.id.login_btn:
                validationAndLogin();
                break;

            case R.id.googlebtn:
                validateAndLoginViaGooglePlus();
                break;

            case R.id.signout_btn:
                signOutGoogleClient();
                break;

            case R.id.revokeaccess_btn:
                revokeAccess();
                break;
        }
    }

    private void revokeAccess() {
        if (mGooglePlusClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGooglePlusClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGooglePlusClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            mGooglePlusClient.connect();

                        }

                    });
        }
    }

    private void signOutGoogleClient() {
        if (mGooglePlusClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGooglePlusClient);
            mGooglePlusClient.disconnect();
            mGooglePlusClient.connect();

        }
    }

    private void validateAndLoginViaGooglePlus() {

        if (!mGooglePlusClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    /**
     * Check for validation of controls and do login then
     */

    private void validationAndLogin() {

        String lUserid = mUserid.getText().toString().trim();
        String lPassword = mPassword.getText().toString().trim();
        if (lUserid == null || lUserid.equals("")) {
            mUserid.setError(getString(R.string.userid_required));
            return;
        }

        if (!Utility.isValidEmail(lUserid)) {
            mUserid.setError(getString(R.string.userid_not_valid));
            return;
        }

        if (lPassword == null || lPassword.equals("")) {
            mPassword.setError(getString(R.string.password_required));
            return;
        }

        mMap = new HashMap<String, Object>();
        mMap.put(USERID, lUserid);
        mMap.put(PASSWORD, lPassword);

        doLogin();
    }

    private void doLogin() {
        EventHandler lHandler = new EventHandler();
        lHandler.doLogin(this, mMap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGooglePlusClient.isConnecting()) {
                mGooglePlusClient.connect();
            }
        }
    }

    /**
     * Notify activity to get updates
     *
     * @param observable
     * @param arg1
     */

    @Override
    public void update(Observable observable, Object arg1) {

        if (observable != null && observable instanceof EventHandler) {
            EventHandler activity_stream = (EventHandler) observable;
            Object result = activity_stream.getResult();
            if (result != null && !result.equals("")
                    && result instanceof String) {

                switch (activity_stream.getType()) {
                    case Constants.TASK_TYPES.LOGIN:
                        registerResult(result);
                        break;


                }

            } else {

                popupCustomDialog(LoginScreen.this, getString(R.string.app_name),
                        getString(R.string.request_for_data_again),
                        getString(R.string.yes), getString(R.string.no));

            }

        }

    }

    /**
     * Get result after successfully login
     *
     * @param result
     */

    private void registerResult(Object result) {


        AppController.handleEvent(LoginScreen.this, Constants.ACTIVITY_STATES.DASHBOARD, "");


    }


    /**
     * Show dialog to the screen, if erros occur
     *
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param negText
     */

    public void popupCustomDialog(final Context context, String title,
                                  String message, String positiveText, String negText) {
        AlertDialog.Builder dialogdbuilder = new AlertDialog.Builder(context);
        if (title != null && title.length() > 0)
            dialogdbuilder.setTitle(title);
        dialogdbuilder.setMessage(message);
        dialogdbuilder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                doLogin();
            }
        });
        dialogdbuilder.setNegativeButton(negText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        AlertDialog dialog = dialogdbuilder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Person lPerson = Plus.PeopleApi.getCurrentPerson(mGooglePlusClient);
        String lEmail = Plus.AccountApi.getAccountName(mGooglePlusClient);
        Toast.makeText(LoginScreen.this, lEmail, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGooglePlusClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGooglePlusClient.connect();
            }
        }
    }


    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }

    protected void onStart() {
        super.onStart();
        mGooglePlusClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGooglePlusClient.isConnected()) {
            mGooglePlusClient.disconnect();
        }
    }
}
