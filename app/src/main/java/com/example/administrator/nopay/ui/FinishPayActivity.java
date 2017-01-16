package com.example.administrator.nopay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.nopay.R;
import com.example.administrator.nopay.utils.MessageHolder;

public class FinishPayActivity extends AppCompatActivity {
    private Button mBtnConfirmPay;
    private TextView mTvMoney;
    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_pay);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String money = intent.getStringExtra("number");
        mBtnConfirmPay = (Button)findViewById(R.id.btn_confirm_pay);
        mTvMoney = (TextView)findViewById(R.id.tv_money);
        mTvMoney.setText(money);
        mTvName = (TextView)findViewById(R.id.tv_name);
        mTvName.setText(getString(R.string.who_get_money, MessageHolder.getInstance().getUserName()));


        mBtnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
