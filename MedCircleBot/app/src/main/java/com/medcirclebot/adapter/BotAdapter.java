package com.medcirclebot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.medcirclebot.MessageModel;
import com.medcirclebot.R;
import com.medcirclebot.other.UserType;

import java.util.List;

public class BotAdapter extends RecyclerView.Adapter {

    private List<MessageModel> mListMessages;

    public BotAdapter(List<MessageModel> list){
        this.mListMessages=list;
    }

    @Override
    public int getItemViewType(int position) {
      return mListMessages.get(position).getUserType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType== UserType.BOT){
           return new BotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bot,parent,false));
       }
       else{
           return new MeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_me,parent,false));
       }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof BotViewHolder){
            ((BotViewHolder) holder).setText(mListMessages.get(position));
        }
        else{
            ((MeViewHolder)holder).setText(mListMessages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mListMessages.size();
    }
}
