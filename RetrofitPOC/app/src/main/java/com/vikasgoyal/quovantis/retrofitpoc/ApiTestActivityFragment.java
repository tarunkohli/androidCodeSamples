package com.vikasgoyal.quovantis.retrofitpoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vikasgoyal.quovantis.moduleapi.common.StringDataDeserializer;
import com.vikasgoyal.quovantis.moduleapi.core.ApiException;
import com.vikasgoyal.quovantis.moduleapi.core.ApiManager;
import com.vikasgoyal.quovantis.moduleapi.core.ApiResponseListener;
import com.vikasgoyal.quovantis.moduleapi.core.ServiceBuilder;
import com.vikasgoyal.quovantis.retrofitpoc.testService.DeviceService;

import retrofit2.Call;

/**
 * A placeholder fragment containing a simple view.
 */
public class ApiTestActivityFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton mBtnExecute;
    private TextView mTxvResultView;

    private Snackbar mSnackbar;
    private ApiDeviceResponseHandler mCallback;

    public ApiTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnExecute = (FloatingActionButton) view.findViewById(R.id.btn_api_test_execute);
        mTxvResultView = (TextView) view.findViewById(R.id.txv_api_test_result);
        mBtnExecute.setOnClickListener(this);
        mTxvResultView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View v) {
        mSnackbar = Snackbar.make(v, "Executing Api", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null);
        mSnackbar.show();

        fireApiRequest();
    }

    private void fireApiRequest() {
        DeviceService service = new ServiceBuilder<DeviceService>()
                .addClass(DeviceService.class)
                .addJsonMapper(String.class, new StringDataDeserializer(), null, null)
                .build();
        Call<String> call = service.getSupportedDevice();
        mCallback = new ApiDeviceResponseHandler();
        call.enqueue(ApiManager.getInstance().addInCallbackHandler(mCallback));
    }

    private class ApiDeviceResponseHandler implements ApiResponseListener<String> {
        @Override
        public void onResponse(ApiException pApiException, String pResponse) {
            if (mSnackbar.isShown()) {
                mSnackbar.dismiss();
            }
            mTxvResultView.setText(pResponse);
        }
    }
}
