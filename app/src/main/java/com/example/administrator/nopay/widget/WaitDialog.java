package com.example.administrator.nopay.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.administrator.nopay.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author swd
 * @date 2017/1/16
 * @time 14:09
 * @description
 */
public class WaitDialog extends Dialog{
    private ImageView imageView;
    private Timer mCheckTimer;
    private int mCheckImgPosition = 0;
    private OnTimeFinishListener mOnTimeFinishListener;
    public WaitDialog(Context context) {
        super(context, R.style.DialogTheme);
    }

    public void setListener(OnTimeFinishListener onTimeFinishListener){
        this.mOnTimeFinishListener = onTimeFinishListener;
    }

    public interface OnTimeFinishListener {
        void onImageClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        imageView = (ImageView)findViewById(R.id.iv_wait_progress);
    }

    private void setCheckTimerTask() {
        mCheckTimer = new Timer();
        mCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                checkEnvironmentHandler.sendMessage(message);
            }
        }, 0, 200);//0ms后   10ms执行一次
    }

    private Handler checkEnvironmentHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgId = msg.what;
            mCheckImgPosition++;
            switch (msgId) {
                case 1:
                    if (mCheckImgPosition%3 == 0) {
                        imageView.setBackgroundResource(R.drawable.dialog_progress1);
                    } else if (mCheckImgPosition%3 == 1) {
                        imageView.setBackgroundResource(R.drawable.dialog_progress2);
                    } else if (mCheckImgPosition%3 == 2) {
                        imageView.setBackgroundResource(R.drawable.dialog_progress3);
                    }

                    if (mCheckImgPosition==5){
                        dismiss();
                        mOnTimeFinishListener.onImageClick();
                    }
                    break;
            }
        }
    };
    @Override
    public void show(){
        super.show();
        setCheckTimerTask();
    }

    @Override
    public void dismiss(){
        super.dismiss();
        if (mCheckTimer!=null){
            mCheckTimer.cancel();
            mCheckImgPosition=0;
        }

    }
}
