package com.example.ajay.animationswithopengl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ajay.animationswithopengl.CustomAnimations.MyAnimation;
import com.example.ajay.animationswithopengl.R;
import com.easyandroidanimations.library.*;

/**
 * Created by ajay on 30/3/16.
 */
public class CustomAnimationsFragmentAdapter extends BaseAdapter {

    private String[] mDataList;
    private Context mContext;

    public CustomAnimationsFragmentAdapter(Context context, String[] pDataList){

        mContext = context;
        mDataList = pDataList;
    }

    private class RowHolder{
        LinearLayout mLayout;
        TextView mText;

        public RowHolder(View convertView){
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

        RowHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_custom_animations_fragment, null);
            holder = new RowHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (RowHolder) convertView.getTag();
        }

        holder.mText.setText(mDataList[position]);
        holder.mLayout.setBackgroundColor(getColor());
        setOnClickListenerForRow(holder, position);

        return convertView;
    }

    /**
     * OnClickListener for row
     * @param holder
     * @param position
     */
    private void setOnClickListenerForRow(RowHolder holder, final int position){

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                switch(position){

                    case 0:
                        new com.easyandroidanimations.library.BlinkAnimation(v).animate();
                        break;

                    case 1:
                        new HighlightAnimation(v).animate();
                        break;

                    case 2:
                        new ExplodeAnimation(v).setExplodeMatrix(ExplodeAnimation.MATRIX_2X2)
                                .setListener(new AnimationListener() {
                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        v.setVisibility(View.VISIBLE);
                                    }
                                })
                                .animate();
                        break;

                    case 3:
                        new SlideInAnimation(v).animate();
                        break;

                    case 4:
                        new MyAnimation(v).animate();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * Method to fetch color for row
     * @return
     */
    private int getColor(){
        return R.color.colorPrimary;
    }
}
