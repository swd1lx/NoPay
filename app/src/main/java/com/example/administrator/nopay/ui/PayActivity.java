package com.example.administrator.nopay.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.nopay.R;
import com.example.administrator.nopay.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class PayActivity extends AppCompatActivity {
    private TextView mTvUserName;
    private EditText mEtMoney;
    private Button mButton;

    private GridView gridView;

    private ScrollView scrollView;

    private ArrayList<Map<String, String>> valueList;

    private Animation enterAnim;

    private Animation exitAnim;

    private VirtualKeyboardView virtualKeyboardView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pay);

        initAnim();

        initView();

        valueList = virtualKeyboardView.getValueList();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    private void initView() {
        handler = new Handler();
        scrollView = (ScrollView)findViewById(R.id.sl_main);
        mButton = (Button)findViewById(R.id.btn_confirm_pay);
        mTvUserName = (TextView)findViewById(R.id.tv_user_name);
        mTvUserName.setText("向个人用户\"150\"转账");
        mEtMoney = (EditText)findViewById(R.id.et_money);

        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            mEtMoney.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mEtMoney, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        mEtMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(runnable, 400);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this,FinishPay.class);
                intent.putExtra("number",mEtMoney.getText().toString());
                startActivity(intent);
            }
        });

        handler.postDelayed(runnable, 400);
    }

    Runnable runnable=new Runnable(){
        @Override
        public void run() {
            virtualKeyboardView.setFocusable(true);
            virtualKeyboardView.setFocusableInTouchMode(true);

            virtualKeyboardView.startAnimation(enterAnim);
            virtualKeyboardView.setVisibility(View.VISIBLE);
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
//            handler.postDelayed(this, 200);
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = mEtMoney.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                mEtMoney.setText(amount);

                Editable ea = mEtMoney.getText();
                mEtMoney.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = mEtMoney.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        mEtMoney.setText(amount);

                        Editable ea = mEtMoney.getText();
                        mEtMoney.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = mEtMoney.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        mEtMoney.setText(amount);

                        Editable ea = mEtMoney.getText();
                        mEtMoney.setSelection(ea.length());
                    }
                }
            }
        }
    };
}
