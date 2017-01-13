package com.example.administrator.nopay.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.nopay.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEtEdit;
    private Button mBtnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mEtEdit = (EditText) findViewById(R.id.edit);
        mBtnSearch = (Button)findViewById(R.id.button);
        mBtnSearch.setOnClickListener(this);

        Intent intent = new Intent(MainActivity.this,PayActivity.class);
        intent.putExtra("name",mEtEdit.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnSearch){
            Intent intent = new Intent(MainActivity.this,PayActivity.class);
            intent.putExtra("name",mEtEdit.getText().toString());
            startActivity(intent);
        }
    }
}
