package com.example.administrator.nopay;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends AppCompatActivity {
    private TextView mTvUserName;
    private EditText mEtMoney;


    private RelativeLayout rl_keyboard;
    private KeyboardUtil keyboardUtil;
    private int change_type;
    private XEditText et_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pay);

        mTvUserName = (TextView)findViewById(R.id.tv_user_name);
        mTvUserName.setText("向个人用户\"150\"转账");
        mEtMoney = (EditText)findViewById(R.id.et_money);

        initKeyboard();
        initTestBtn();
    }

    private void initTestBtn() {
        change_type = KeyboardUtil.TYPE_PRICE;
        showKeyBoard();
    }

    private void initKeyboard() {
        et_amount = (XEditText) findViewById(R.id.et_amount);
        keyboardUtil = new KeyboardUtil(this, et_amount);
        rl_keyboard = (RelativeLayout) findViewById(R.id.rl__keyboard);
        et_amount.setInputType(InputType.TYPE_NULL);
        keyboardUtil.setKeyboardListener(new KeyboardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                String result = et_amount.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case KeyboardUtil.TYPE_NUMBER:
                            msg += "num:"+result;
                            break;
                        case KeyboardUtil.TYPE_PRICE:
                            msg += "price:"+result;
                            break;
                        default:
                            msg += "input:"+result;
                            break;
                    }
                    Toast.makeText(PayActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard();
                change_type = -1;
            }
        });
        et_amount.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                Editable editable = ((EditText) view).getText();
                int start = ((EditText) view).getSelectionStart();
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }
        });
    }
        /**
         * 显示键盘
         */
    protected void showKeyBoard() {
        rl_keyboard.setVisibility(View.VISIBLE);
        et_amount.setText("");
        switch (change_type) {
            case KeyboardUtil.TYPE_NUMBER:
                et_amount.setHint("请输入数量");
                break;

            case KeyboardUtil.TYPE_PRICE:
                et_amount.setHint("请输入价格");
                break;

            default:
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
    }

    /**
     * 显示键盘
     */
    protected void hideKeyBoard() {
        rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                hideKeyBoard();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
