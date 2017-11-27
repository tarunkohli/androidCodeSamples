package com.medcirclebot.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.medcirclebot.MessageModel;
import com.medcirclebot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BotViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_bot)
    protected TextView mBotTV;
    @BindView(R.id.tv_time)  protected TextView mTimeTV;

    public BotViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setText(MessageModel messageModel) {
        mBotTV.setText(Html.fromHtml(messageModel.getMessage()));
        mTimeTV.setText(messageModel.getTime());
    }
}
