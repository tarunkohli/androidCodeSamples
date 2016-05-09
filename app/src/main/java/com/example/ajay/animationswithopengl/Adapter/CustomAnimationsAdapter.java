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

import com.example.ajay.animationswithopengl.Fragments.ActivityTransitionsFragment;
import com.example.ajay.animationswithopengl.Fragments.CircularRevealFragment;
import com.example.ajay.animationswithopengl.Fragments.CustomAnimationsFragment;
import com.example.ajay.animationswithopengl.Fragments.FragmentTransitions;
import com.example.ajay.animationswithopengl.R;

/**
 * Created by ajay on 22/3/16.
 */
public class CustomAnimationsAdapter extends BaseAdapter {

    private String[] mDataList;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private FrameLayout mFrame;
    private ListView mList;

    public CustomAnimationsAdapter(Context context,String[] dataList,FragmentManager pFragmentManager,
                                   FrameLayout pFrame,ListView pList){
        mContext = context;
        mDataList = dataList;
        mFragmentManager = pFragmentManager;
        mFrame = pFrame;
        mList = pList;
    }

    /**
     * RowHolder class
     */
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_custom_animations,null);
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
     * Method to set onClickListener for row click
     * @param holder
     */
    private void setOnClickListenerForRow(final RowHolder holder, final int position){

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;

                switch(position){
                    case 0:
                        fragment = new ActivityTransitionsFragment();
                        break;

                    case 1:
                        fragment = new CustomAnimationsFragment();
                        break;

                    case 2:
                        fragment = new CircularRevealFragment();
                        break;

                    case 3:
                        fragment = new FragmentTransitions();
                        break;

                    default:
                        break;
                }

                mFragmentManager.beginTransaction()
                        .replace(mFrame.getId(), fragment)
                        .commit();
                mList.setVisibility(View.GONE);
                mFrame.setVisibility(View.VISIBLE);
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