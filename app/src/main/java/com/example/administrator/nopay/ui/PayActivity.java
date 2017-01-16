package com.example.administrator.nopay.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.nopay.R;
import com.example.administrator.nopay.utils.MessageHolder;
import com.example.administrator.nopay.widget.PopEnterPassword;
import com.example.administrator.nopay.widget.VirtualKeyboardView;
import com.example.administrator.nopay.widget.WaitDialog;

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
    private String totalMoney;
    private WaitDialog waitDialog;
    private ImageView mIvBack;
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
        mIvBack = (ImageView)findViewById(R.id.iv_back);
        mTvUserName = (TextView)findViewById(R.id.tv_user_name);
        mTvUserName.setText(getString(R.string.user_name, MessageHolder.getInstance().getUserName()));
        mEtMoney = (EditText)findViewById(R.id.et_money);
        waitDialog = new WaitDialog(PayActivity.this);

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
                waitDialog.show();
                waitDialog.setListener(new WaitDialog.OnTimeFinishListener() {
                    @Override
                    public void onImageClick() {
                        String etMoney = mEtMoney.getText().toString();
                        if (etMoney.contains(".")){
                            if (etMoney.split("\\.")[1].length()==1){
                                totalMoney = etMoney+"0";
                            } else if (etMoney.split("\\.")[1].length()==2){
                                totalMoney = etMoney;
                            }
                        } else {
                            totalMoney = etMoney+".00";
                        }

                        PopEnterPassword popEnterPassword = new PopEnterPassword(PayActivity.this,MessageHolder.getInstance().getUserName(),totalMoney);

                        // 显示窗口
                        popEnterPassword.showAtLocation(PayActivity.this.findViewById(R.id.btn_confirm_pay),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
                    }
                });
            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
