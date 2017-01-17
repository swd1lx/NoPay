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
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private EditText mEtEdit;
    private Button mBtnSearch;
    private Button mBtnCode;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mEtEdit = (EditText) findViewById(R.id.edit);
        mBtnSearch = (Button)findViewById(R.id.button);
        mBtnCode = (Button)findViewById(R.id.btn_code);
        mBtnCode.setOnClickListener(this);
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
            return;
        }

        if (view==mBtnCode){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MipcaActivityCapture.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mEtEdit.setText(bundle.getString("result"));
                    //显示
//                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}
