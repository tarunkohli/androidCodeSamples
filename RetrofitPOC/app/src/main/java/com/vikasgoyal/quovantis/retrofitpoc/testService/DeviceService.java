package com.vikasgoyal.quovantis.retrofitpoc.testService;


import retrofit2.Call;
import retrofit2.http.GET;

public interface DeviceService {

    String SUPPORTED_DEVICE_LIST = "/RestAccountService.svc/GetInstrumentList";


    @GET(SUPPORTED_DEVICE_LIST)
    Call<String> getSupportedDevice();
}
