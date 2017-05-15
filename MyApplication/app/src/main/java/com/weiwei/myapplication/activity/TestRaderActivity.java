package com.weiwei.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.weiwei.myapplication.R;
import com.weiwei.myapplication.view.widget.RaderView;

public class TestRaderActivity extends AppCompatActivity {
    RaderView raderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rader);
        raderView = (RaderView) findViewById(R.id.rader_view);
//        raderView.startRader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        raderView.startRader();
    }

    @Override
    protected void onPause() {
        super.onPause();
        raderView.stopRader();
    }
}
