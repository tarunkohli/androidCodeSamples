package com.example.ajay.animationswithopengl.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ajay.animationswithopengl.Fragments.AnimatorSetFragment;
import com.example.ajay.animationswithopengl.Fragments.ObjectAnimatorFragment;
import com.example.ajay.animationswithopengl.Fragments.ValueAnimatorFragment;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 27/2/16.
 */
public class PropertyAnimationsAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mDataList;
    private FrameLayout mFrame;
    private FragmentManager mFragmentManager;
    private ListView mListView;

    public PropertyAnimationsAdapter(Context pContext,String[] pDataList,
                                     FrameLayout pFrame,ListView pListView,FragmentManager pFragmentManager){
        mContext = pContext;
        mDataList = pDataList;
        mFrame = pFrame;
        mFragmentManager = pFragmentManager;
        mListView = pListView;
    }

    /**
     * ViewHolder class
     */
    private class RowHolder{
        private LinearLayout mAnimationLayout;
        private TextView mAnimationText;

        public RowHolder(View convertView){
            mAnimationLayout = (LinearLayout) convertView.findViewById(R.id.animation_layout);
            mAnimationText = (TextView) convertView.findViewById(R.id.animation_name);
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

        RowHolder rowHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_property_animations,null);
            rowHolder = new RowHolder(convertView);
            convertView.setTag(rowHolder);
        }else{
            rowHolder = (RowHolder) convertView.getTag();
        }

        rowHolder.mAnimationText.setText(mDataList[position]);
        rowHolder.mAnimationLayout.setBackgroundColor(getColor());

        setOnClickListenerForRow(rowHolder, position);
        return convertView;
    }

    /**
     * OnClickListener for row click
     *
     * @param pHolder
     * @param position
     */
    private void setOnClickListenerForRow(RowHolder pHolder, final int position){

        pHolder.mAnimationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;

                switch(position){

                    case 0:
                        fragment = new ValueAnimatorFragment();
                        break;

                    case 1:
                        fragment = new ObjectAnimatorFragment();
                        break;

                    case 2:
                        fragment = new AnimatorSetFragment();
                        break;

                    default:
                        break;
                }

                mFragmentManager.beginTransaction()
                        .replace(mFrame.getId(), fragment)
                        .commit();
                mListView.setVisibility(View.GONE);
                mFrame.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Method to fetch color for the card
     * @return
     */
    private int getColor(){
        return R.color.colorPrimary;
    }
}