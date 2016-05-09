package com.example.ajay.animationswithopengl.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;
import com.example.ajay.animationswithopengl.Adapter.MyAdapter;
import com.example.ajay.animationswithopengl.R;

public class MainActivity extends AppCompatActivity {

    private ListView mList;
    private String[] mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mData = getResources().getStringArray(R.array.list_elements);
        mList = (ListView) findViewById(R.id.list);

        MyAdapter lAdapter = new MyAdapter(this,mData);
        mList.setAdapter(lAdapter);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
    }
}