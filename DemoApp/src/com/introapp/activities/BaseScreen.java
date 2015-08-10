package com.introapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.app.intro.R;

import java.util.Observable;
import java.util.Observer;


/**
 * Base class for all other classes, extends and get action bar common to all screens
 */

public class BaseScreen extends ActionBarActivity implements Observer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_screen);
    }


    /**
     * Method will notify once change in the object
     * @param observable
     * @param o
     */

    @Override
    public void update(Observable observable, Object o) {

    }
}
