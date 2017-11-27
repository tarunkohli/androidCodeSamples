package com.medcirclebot.other;

import ai.api.model.AIError;
import ai.api.model.AIResponse;

public interface AiResponseListener {
    void onSuccessResult(final AIResponse response);
    void onError(final AIError error);
}
