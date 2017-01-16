package com.example.administrator.nopay.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.nopay.R;
import com.example.administrator.nopay.utils.MessageHolder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEtEdit;
    private Button mBtnSearch;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mEtEdit = (EditText) findViewById(R.id.edit);
        mBtnSearch = (Button)findViewById(R.id.button);
        mBtnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnSearch){
            if (mEtEdit.getText().toString().equals("")){
                return;
            }
            mp = MediaPlayer.create(this, R.raw.qrcode_completed);
            mp.start();

            MessageHolder.getInstance().setUserName(mEtEdit.getText().toString());
            Intent intent = new Intent(MainActivity.this,PayActivity.class);
            startActivity(intent);
        }
    }
}
