package com.introapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.intro.R;
import com.introapp.library.util.Constants;
import com.introapp.library.util.EventHandler;
import com.introapp.library.util.Utility;

import java.util.HashMap;
import java.util.Observable;

/**
 * Created by sachit on 7/3/2015.
 */
public class ForgotPasswordScreen extends BaseScreen implements View.OnClickListener, Constants.APPKEYS {

    private EditText mUserid;
    private Button mSubmit;
    private HashMap<String, Object> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        findViewById();
    }

    /**
     * find id's of all controls used in Activity
     */

    private void findViewById() {

        mUserid     =   (EditText) findViewById(R.id.userid_edittext);
        mSubmit     =   (Button) findViewById(R.id.submit_btn);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.submit_btn:
                validationAndfetchPassword();
                break;
        }


    }

    /**
     * Check for validation of controls and do login then
     */

    private void validationAndfetchPassword() {

        String lUserid          =   mUserid.getText().toString().trim();
        if(lUserid==null || lUserid.equals("")) {
            mUserid.setError(getString(R.string.userid_required));
            return;
        }

        if(!Utility.isValidEmail(lUserid)) {
            mUserid.setError(getString(R.string.userid_not_valid));
            return;
        }

        mMap =   new HashMap<String, Object>();
        mMap.put(USERID, lUserid);

        fetchPassword();

    }

    private void fetchPassword() {
        EventHandler lHandler   =   new EventHandler();
        lHandler.fetchPassword(this, mMap);
    }

    /**
     * Notify activity to get updates
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
                    case Constants.TASK_TYPES.FORGOT_PASSWORD:
                        registerResult(result);
                        break;


                }

            } else {

                popupCustomDialog(ForgotPasswordScreen.this, getString(R.string.app_name),
                        getString(R.string.request_for_data_again),
                        getString(R.string.yes), getString(R.string.no));

            }

        }

    }

    /**
     * Get result after successfully login
     * @param result
     */

    private void registerResult(Object result) {


    }


    /**
     * Show dialog to the screen, if erros occur
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

                fetchPassword();
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


}
