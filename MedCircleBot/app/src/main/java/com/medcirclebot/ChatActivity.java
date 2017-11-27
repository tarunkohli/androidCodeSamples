package com.medcirclebot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.medcirclebot.adapter.BotAdapter;
import com.medcirclebot.other.AiResponseListener;
import com.medcirclebot.other.Config;
import com.medcirclebot.other.UserType;
import com.medcirclebot.other.Util;

import java.util.ArrayList;
import java.util.List;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.model.Status;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends AppCompatActivity implements AiResponseListener {

    @BindView(R.id.et_type_message) protected EditText mSendMessageET;
    @BindView(R.id.rv_chat) protected RecyclerView mChatRV;
    private AIDataService aiDataService;
    private BotAdapter mAdapter;
    private List<MessageModel> mMessageList=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initView();
        initService();
        new BotChatAsync(aiDataService,this).execute("initial message");
    }

    private void initView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mChatRV.setLayoutManager(layoutManager);
    }

    private void setAdapter(){
        if(mAdapter==null){
            mAdapter=new BotAdapter(mMessageList);
            mChatRV.setAdapter(mAdapter);
        }
        else{
            int lastPosition=mMessageList.size();
            mAdapter.notifyItemChanged(lastPosition);
            mChatRV.smoothScrollToPosition(mChatRV.getAdapter().getItemCount());
        }
    }



    private void initService() {
        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(this, config);

    }

    @OnClick(R.id.bt_send)
    protected void onSendClicked() {
       String message=mSendMessageET.getText().toString().trim();
        if(message.equals("")){
            Toast.makeText(this,"Please type message",Toast.LENGTH_SHORT).show();
            return;
        }

        setDataInList(message, UserType.ME);
        new BotChatAsync(aiDataService,this).execute(message);
    }

    private void setDataInList(String message, byte userType) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);
        messageModel.setUserType(userType);
        messageModel.setTime(Util.getTimeInAmPM());
        mMessageList.add(messageModel);
        setAdapter();
    }


    @Override
    public void onSuccessResult(AIResponse response) {
        final Status status = response.getStatus();
        final Result result = response.getResult();
        final String speech = result.getFulfillment().getSpeech();
        setDataInList(speech, UserType.BOT);
        mSendMessageET.setText("");
    }

    @Override
    public void onError(AIError error) {
        Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show();
    }
}
