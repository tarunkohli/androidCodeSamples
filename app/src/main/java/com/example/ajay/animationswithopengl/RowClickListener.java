package com.example.ajay.animationswithopengl;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ajay on 13/2/16.
 */
public class RowClickListener implements View.OnClickListener {

    private LinearLayout mLayout;
    private Context mContext;
    private boolean mExpandFlag;

    public RowClickListener(Context pContext){

        mContext = pContext;
    }

    @Override
    public void onClick(View v) {

        if(mExpandFlag){
            mLayout.setVisibility(View.VISIBLE);
            mExpandFlag = false;
        }else{
            mLayout.setVisibility(View.GONE);
            mExpandFlag = true;
        }
    }

    public LinearLayout getmLayout() {
        return mLayout;
    }

    public void setmLayout(LinearLayout mLayout) {
        this.mLayout = mLayout;
    }

    public boolean ismExpandFlag() {
        return mExpandFlag;
    }

    public void setmExpandFlag(boolean mExpandFlag) {
        this.mExpandFlag = mExpandFlag;
    }
}