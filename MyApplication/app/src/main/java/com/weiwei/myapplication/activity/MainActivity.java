package com.weiwei.myapplication.activity;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.weiwei.myapplication.R;
import com.weiwei.myapplication.view.widget.MyWaveView;
import com.weiwei.myapplication.view.widget.MyWaveView2;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    private float progress;
    private int mCircleProgress;
    //    LD_WaveView waveCircleView;//圆形
    private Button btReset;
    ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyWaveView myVaveView = (MyWaveView) findViewById(R.id.mWave);
        final MyWaveView myWaveViewRect = (MyWaveView) findViewById(R.id.rect_wave);
        final MyWaveView2 shaderWaveView = (MyWaveView2) findViewById(R.id.wave_shader);
//        waveCircleView = (LD_WaveView) findViewById(R.id.circle_wave);
        objectAnimator = ObjectAnimator.ofFloat(shaderWaveView, "waveShiftRation", 0.0f, 0.05f);
        objectAnimator.setDuration(10000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(Integer.MAX_VALUE);

        findViewById(R.id.bt_reset).setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               progress = 0.0f;
                                                               mHandler.sendEmptyMessageDelayed(1, 1000);
                                                           }
                                                       }
        );
        mHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
//                super.dispatchMessage(msg);
//                Progress+=5;
                progress += 0.03f;
                mCircleProgress++;
                if (mCircleProgress > 100) {
                    mCircleProgress = 100;
                }
//                waveCircleView.setmProgress(mCircleProgress);
                if (progress > 1.0f) {
                    progress = 1.0f;

                } else {
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }

                myVaveView.setProgress(progress);
                myWaveViewRect.setProgress(progress);
            }
        };
        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        objectAnimator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        objectAnimator.cancel();
    }
}
