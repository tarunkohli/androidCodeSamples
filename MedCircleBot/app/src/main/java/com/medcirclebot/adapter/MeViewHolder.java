package com.medcirclebot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.medcirclebot.MessageModel;
import com.medcirclebot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_me)  protected TextView mMeTV;
    @BindView(R.id.tv_time)  protected TextView mTimeTV;

    public MeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setText(MessageModel messageModel){
        mMeTV.setText(messageModel.getMessage());
        mTimeTV.setText(messageModel.getTime());
    }

}
