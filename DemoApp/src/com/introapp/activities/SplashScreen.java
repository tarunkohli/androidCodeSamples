package com.introapp.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import com.app.intro.R;
import com.introapp.app.AppController;
import com.introapp.library.util.Constants;


public class SplashScreen extends Activity {

    private static Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            public void run() {

                AppController.handleEvent(SplashScreen.this, Constants.ACTIVITY_STATES.LOGIN);

                finish();
            }
        }, 3000);
    }

    public static Context getSelf() {
        return mContext;
    }


}
