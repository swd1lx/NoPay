package com.example.administrator.nopay.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.nopay.R;
import com.example.administrator.nopay.ui.FinishPayActivity;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;

    private String mUsername;

    private String mTextAmount;

    private TextView mTvTextAmount;

    private TextView mTvUsername;

    private WaitDialog waitDialog;

    public PopEnterPassword(final Activity context,final String username,final String textAmount) {

        super(context);

        this.mContext = context;

        this.mUsername = username;

        this.mTextAmount = textAmount;

        waitDialog = new WaitDialog(context);

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);

        mTvTextAmount = (TextView)mMenuView.findViewById(R.id.textAmount);
        mTvTextAmount.setText(mTextAmount);

        mTvUsername = (TextView)mMenuView.findViewById(R.id.tv_username);
        mTvUsername.setText(mContext.getString(R.string.user_name,mUsername));

        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                waitDialog.show();
                waitDialog.setListener(new WaitDialog.OnTimeFinishListener() {
                    @Override
                    public void onImageClick() {
                        dismiss();
                        Intent intent = new Intent(context, FinishPayActivity.class);
                        intent.putExtra("number",mTextAmount);
                        mContext.startActivity(intent);
                    }
                });
            }
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }
}
