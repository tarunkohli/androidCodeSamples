package com.qtsachit.helper.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.qtsachit.helper.util.picker.Pickable;
import com.qtsachit.helper.util.picker.TimePickerFragment;

/**
 * The class is Created by Sachit on 01/February/2017
 * <p>
 * Description-
 * <p>
 * Additional notes-
 */
public class MainActivity extends AppCompatActivity implements Pickable {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.qtsachit.helper.R.layout.activity_main);

        mEditText = (EditText) findViewById(com.qtsachit.helper.R.id.et_dummy);
        Button button = (Button) findViewById(com.qtsachit.helper.R.id.btn_click);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }

    private void showPicker() {
        TimePickerFragment timePickerFragment   =   new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "TimePicker", this);
    }

    @Override
    public void onPickerDismissed(String selectedDate) {
        LoggerUtility.debug("TAG", selectedDate);
    }
}
