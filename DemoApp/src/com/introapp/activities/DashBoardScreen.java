package com.introapp.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.intro.R;
import com.introapp.app.AppController;
import com.introapp.drawer.DrawerArrowDrawable;
import com.introapp.library.util.Constants;
import com.introapp.library.util.Utility;
import com.introapp.util.Util;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.START;

/**
 * Created by sachit on 7/3/2015.
 */
public class DashBoardScreen extends FragmentActivity {
    private DrawerArrowDrawable mDrawerArrowDrawable;
    private float offset;
    private boolean flipped;
    private DrawerLayout mDrawer;
    private int mLastBackStackEntryCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarColor();
        setContentView(R.layout.dashboard);
        findViewById();
        addDefaultFragment();
        addOnBackStackChangedListener();
    }


    private void addOnBackStackChangedListener(){

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fManager = getSupportFragmentManager();

                if(fManager.getBackStackEntryCount() == 0){
//                    mHeader.setText(mLeftMenuItems.get(1).getmName());//For HomeFragment

                }else if(mLastBackStackEntryCount > fManager.getBackStackEntryCount()){//Means, Fragmnets are just popped up
                    FragmentManager.BackStackEntry entry = fManager.getBackStackEntryAt(fManager.getBackStackEntryCount() - 1);
                    String name = entry.getName();
//                    mHeader.setText(null != name ? name : new String());
                }

                mLastBackStackEntryCount = fManager.getBackStackEntryCount();
            }
        });
    }


    /**
     * Set status bar color according to your action bar
     */

    private void setStatusBarColor() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
//        }
    }

    private void findViewById() {

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        final Resources resources = getResources();
        mDrawerArrowDrawable = new DrawerArrowDrawable(resources);
        mDrawerArrowDrawable.setStrokeColor(resources.getColor(R.color.white));
        imageView.setImageDrawable(mDrawerArrowDrawable);
        mDrawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    mDrawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    mDrawerArrowDrawable.setFlip(flipped);
                }

                mDrawerArrowDrawable.setParameter(offset);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDrawer();
            }
        });

    }


    private AdapterView.OnItemClickListener leftMenuItemClickListener	=	new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {

            selectItem(arg2);
            handleDrawer();

        }
    };


    protected void handleDrawer() {

        if (mDrawer.isDrawerVisible(START)) {
            mDrawer.closeDrawer(START);
        } else {
            mDrawer.openDrawer(START);
        }
    }


    private void addDefaultFragment(){
//        Fragment fragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//        mLeftMenuList.setItemChecked(1, true);
//        mHeader.setText(mLeftMenuItems.get(1).getmName());
    }



    public void selectItem(int pPosition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (pPosition) {
            case 0:
                return;


            default:
                break;
        }

        if(fragment!=null) {
            fragment.setArguments(args);
            FragmentManager fManager = getSupportFragmentManager();
            fManager.popBackStack(Constants.FRAGMENT_TAGS.MAIN_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).addToBackStack(Constants.FRAGMENT_TAGS.MAIN_FRAGMENT)
                    .commit();
//            mLeftMenuList.setItemChecked(pPosition, true);
        }else{
            Log.e("DashboardActivity", "Error in creating fragment");
        }

    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            Utility.popupCustomDialogForExit(DashBoardScreen.this, getString(R.string.app_name), getString(R.string.exit_app),
                    getString(R.string.yes), getString(R.string.no));
        }else{
            super.onBackPressed();
        }

    }
}
