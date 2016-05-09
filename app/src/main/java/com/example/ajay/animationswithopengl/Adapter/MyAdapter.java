package com.example.ajay.animationswithopengl.Adapter;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ajay.animationswithopengl.Activities.CustomAnimations;
import com.example.ajay.animationswithopengl.Activities.DrawableAnimations;
import com.example.ajay.animationswithopengl.Activities.PropertyAnimations;
import com.example.ajay.animationswithopengl.Activities.ViewAnimations;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 31/1/16.
 */
public class MyAdapter extends BaseAdapter{

    private Context mContext;
    private String[] mDataList;

    /**
     * View holder class
     */
    private class MyViewHolder{
        private TextView mTitle;

        public MyViewHolder(View pConvertView){
            mTitle = (TextView) pConvertView.findViewById(R.id.title);
        }
    }

    public MyAdapter(Context pContext,String[] pDataList){
        mContext = pContext;
        mDataList = pDataList;
    }

    @Override
    public int getCount() {
        return mDataList.length;
    }

    @Override
    public Object getItem(int position) {
        return mDataList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder lHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row,null);
            lHolder = new MyViewHolder(convertView);
            convertView.setTag(lHolder);
        }else{
            lHolder = (MyViewHolder) convertView.getTag();
        }

        lHolder.mTitle.setText(mDataList[position]);
        convertView.setBackgroundColor(getViewColor(position));

        setOnClickListenerForCard(convertView, position);
        return convertView;
    }

    /**
     * Card click listener
     * @param convertView
     * @param position
     */
    private void setOnClickListenerForCard(final View convertView, final int position){

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new AsyncTask<String,String,String>(){

                    private Intent lIntent=null;

                    @Override
                    protected void onPreExecute() {
                        Animation animation =
                                AnimationUtils.loadAnimation(mContext, R.anim.tween);

                        switch (position) {
                            case 0:
                                v.startAnimation(animation);
                                lIntent = new Intent(mContext, ViewAnimations.class);
                                break;

                            case 1:
                                ObjectAnimator set = (ObjectAnimator) AnimatorInflater
                                        .loadAnimator(mContext, R.animator.property_animator);
                                set.setTarget(v);
                                set.start();
                                lIntent = new Intent(mContext, PropertyAnimations.class);
                                break;

                            case 2:
                                v.startAnimation(animation);
                                lIntent = new Intent(mContext, DrawableAnimations.class);
                                break;

                            case 3:
                                v.startAnimation(animation);
                                lIntent = new Intent(mContext, CustomAnimations.class);
                                break;

                            default:
                                break;
                        }
                        super.onPreExecute();
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if(lIntent != null) {
                            Bundle bundle = new Bundle();
                            bundle.putBundle("Activity bundle", ActivityOptionsCompat.makeCustomAnimation(
                                    mContext, R.anim.slide_up_animation, R.anim.slide_down).toBundle());
                            bundle.putBundle("shared element bundle", ActivityOptionsCompat
                                    .makeSceneTransitionAnimation((Activity) mContext, convertView, "convertview").toBundle());
                            mContext.startActivity(lIntent, bundle);
                        }
                        super.onPostExecute(s);
                    }
                }.execute();

            }
        });
    }

    /**
     * Method to get the card color
     * @param position
     * @return
     */
    private int getViewColor(int position){

        int color = android.R.color.black;

        switch (position){

                case 0:
                    color = ContextCompat.getColor(mContext,R.color.colorAccent);
                    break;

                case 1:
                    color = ContextCompat.getColor(mContext,android.R.color.holo_purple);
                    break;

                case 2:
                    color = ContextCompat.getColor(mContext,R.color.colorPrimary);
                    break;

                case 3:
                    color = ContextCompat.getColor(mContext,android.R.color.holo_orange_dark);
                    break;

                default:
                    break;
        }

        return color;
    }
}