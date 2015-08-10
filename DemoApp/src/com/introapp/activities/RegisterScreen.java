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

public class RegisterScreen extends BaseScreen implements View.OnClickListener, Constants.APPKEYS{

    private EditText mName;
    private EditText mUserid;
    private EditText mPassword;
    private EditText mPhoneNumber;
    private EditText mCompanyName;
    private EditText mDesignation;
    private Button mRegister;
    private HashMap<String, Object> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        findViewById();
    }

    /**
     * find id's of all controls used in Activity
     */
    private void findViewById() {

        mName           =   (EditText) findViewById(R.id.name_edittext);
        mUserid         =   (EditText) findViewById(R.id.userid_edittext);
        mPassword       =   (EditText) findViewById(R.id.password_edittext);
        mPhoneNumber    =   (EditText) findViewById(R.id.phonenumber_edittext);
        mCompanyName    =   (EditText) findViewById(R.id.companyname_edittext);
        mDesignation    =   (EditText) findViewById(R.id.designation_edittext);
        mRegister       =   (Button) findViewById(R.id.register_btn);
        mRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.register_btn:
                validationAndRegister();
                break;
        }

    }

    /**
     * Check for validation of controls and do login then
     */

    private void validationAndRegister() {

        String lName        =   mName.getText().toString().trim();
        String lUserid      =   mUserid.getText().toString().trim();
        String lPassword    =   mPassword.getText().toString().trim();
        String lPhoneNumber =   mPhoneNumber.getText().toString().trim();
        String lCompanyName =   mCompanyName.getText().toString().trim();
        String lDesignation =   mDesignation.getText().toString().trim();

        if(lUserid==null || lUserid.equals("")) {
            mUserid.setError(getString(R.string.userid_required));
            return;
        }

        if(!Utility.isValidEmail(lUserid)) {
            mUserid.setError(getString(R.string.userid_not_valid));
            return;
        }


        if(lPassword==null || lPassword.equals("")) {
            mPassword.setError(getString(R.string.password_required));
            return;
        }


        if(lName==null || lName.equals("")) {
            mName.setError(getString(R.string.name_required));
            return;
        }


        if(lPhoneNumber==null || lPhoneNumber.equals("")) {
            mPhoneNumber.setError(getString(R.string.phonenumber_required));
            return;
        }


        if(lCompanyName==null || lCompanyName.equals("")) {
            mCompanyName.setError(getString(R.string.companyname_required));
            return;
        }

        if(lDesignation==null || lDesignation.equals("")) {
            mDesignation.setError(getString(R.string.designation_required));
            return;
        }

        mMap =   new HashMap<String, Object>();
        mMap.put(NAME, lName);
        mMap.put(USERID, lUserid);
        mMap.put(PASSWORD, lPassword);
        mMap.put(PHONENUMBER, lPhoneNumber);
        mMap.put(COMPANYNAME, lCompanyName);
        mMap.put(DESIGNATION, lDesignation);

        doRegister();

    }

    private void doRegister() {
        EventHandler lHandler   =   new EventHandler();
        lHandler.doRegister(this, mMap);
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
                    case Constants.TASK_TYPES.REGISTER:
                        registerResult(result);
                        break;


                }

            } else {

                popupCustomDialog(RegisterScreen.this, getString(R.string.app_name),
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

                doRegister();
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
