package com.example.ajay.animationswithopengl.Adapter;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 15/2/16.
 */
public class ViewAnimationsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflator;
    private String[] mDataList;
    private Animation mAnimation;
    private int mScreenWidth,mScreenHeight;

    public ViewAnimationsAdapter(Context pContext,String[] pDataList){
        mContext = pContext;
        mDataList = pDataList;
        mInflator = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
    }

    private class ViewHolder{
        public LinearLayout mLayout;
        public TextView mText;

        public ViewHolder(View convertView){
            mLayout = (LinearLayout) convertView.findViewById(R.id.animation_layout);
            mText = (TextView) convertView.findViewById(R.id.animation_name);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder lHolder;

        if(convertView == null){
            convertView = mInflator.inflate(R.layout.list_view_animations,null);
            lHolder = new ViewHolder(convertView);
            convertView.setTag(lHolder);
        }else{
            lHolder = (ViewHolder) convertView.getTag();
        }

        lHolder.mText.setText(mDataList[position]);
        lHolder.mLayout.setBackgroundColor(getViewColor());
        setOnClickListenerForCard(lHolder, position);
        return convertView;
    }

    /**
     * OnClickListener for card click
     * @param pHolder
     * @param position
     */
    private void setOnClickListenerForCard(final ViewHolder pHolder,final int position){

        pHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                switch(position){

                    case 0:
                        //Tween alpha through XML
                        mAnimation = AnimationUtils
                                .loadAnimation(mContext, R.anim.tween);

                        //Uncomment to Tween alpha through AlphaAnimation object
//                        mAnimation = new AlphaAnimation(1,0);
//                        mAnimation.setDuration(2000);
                        break;

                    case 1:
                        //Tween scale through XML
                        mAnimation = AnimationUtils
                                .loadAnimation(mContext, R.anim.scaling);

                        //Uncomment to Tween scale through ScaleAnimation object
//                        mAnimation = new ScaleAnimation(1f,1.2f,1f,1.2f,pHolder.mLayout.getPivotX(),
//                                pHolder.mLayout.getPivotY());
//                        mAnimation.setDuration(500);
                        break;

                    case 2:
                        mAnimation = AnimationUtils
                                .loadAnimation(mContext, R.anim.rotation);
                        break;

                    case 3:
                        mAnimation = AnimationUtils
                                .loadAnimation(mContext, R.anim.transform);
                        break;

                    case 4:
                        mAnimation = AnimationUtils
                                .loadAnimation(mContext, R.anim.custom_view_animations);
                        break;

                    default:
                        break;
                }

                v.startAnimation(mAnimation);
            }
        });
    }

    private int getViewColor(){
        return R.color.colorPrimary;
    }
}
