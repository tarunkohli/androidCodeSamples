package com.vikasgoyal.quovantis.retrofitpoc.testService;

import com.google.gson.annotations.SerializedName;

public class SupportedDeviceResponse {

    @SerializedName("Data")
    private String mAppDevices;

    public String getAppDevices() {
        return mAppDevices;
    }
}
