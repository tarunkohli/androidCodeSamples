package com.medcirclebot;

import android.os.AsyncTask;

import com.medcirclebot.other.AiResponseListener;

import ai.api.AIServiceException;
import ai.api.android.AIDataService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class BotChatAsync extends AsyncTask<String, Void, AIResponse> {

    private AIDataService mAiDataService;
    private AIError mAiError;
    private AiResponseListener iListener;

    public BotChatAsync(AIDataService aiService, AiResponseListener iListener){
     this.mAiDataService =aiService;
     this.iListener=iListener;
    }

    @Override
    protected AIResponse doInBackground(String... strings) {
        final AIRequest request = new AIRequest();
        request.setQuery(strings[0]);
        //RequestExtras requestExtras = null;
        try {
            return mAiDataService.request(request);
        } catch (final AIServiceException e) {
            mAiError = new AIError(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(AIResponse aiResponse) {
        if (aiResponse != null) {
            iListener.onSuccessResult(aiResponse);
        } else {
            iListener.onError(mAiError);
        }
    }
    }
