package com.weiwei.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.weiwei.myapplication.R;
import com.weiwei.myapplication.view.widget.CircleBerzierView;

public class TestBazerActivity extends AppCompatActivity {
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private SeekBar seekBar4;
    private CircleBerzierView circleBerzierView;
    private CircleBerzierView circleBerzierView2;

    private static final String TAG = "TestBazerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bazer);
        circleBerzierView = (CircleBerzierView) findViewById(R.id.bazer_view);
        circleBerzierView.setVisibility(View.GONE);
        circleBerzierView2 = (CircleBerzierView) findViewById(R.id.bazer_view2);
        circleBerzierView2.setFlag(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seekBar1 = (SeekBar) findViewById(R.id.seek1);
        seekBar2 = (SeekBar) findViewById(R.id.seek2);
        seekBar3 = (SeekBar) findViewById(R.id.seek3);
        seekBar4 = (SeekBar) findViewById(R.id.seek4);
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float temp = seekBar.getProgress() * 1.0f / 100;
                circleBerzierView.setBottomRation(temp);
                circleBerzierView2.setBottomRation(temp);

            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float temp = seekBar.getProgress() * 1.0f / 100;
                circleBerzierView.setTopRation(temp);
                circleBerzierView2.setTopRation(temp);
            }
        });
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float temp = seekBar.getProgress() * 1.0f / 100;
                circleBerzierView.setLeftRation(temp);
                circleBerzierView2.setLeftRation(temp);
                Log.d(TAG, "onStopTrackingTouch: ");
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float temp = seekBar.getProgress() * 1.0f / 100;
                circleBerzierView.setRightRation(temp);
                circleBerzierView2.setRightRation(temp);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
