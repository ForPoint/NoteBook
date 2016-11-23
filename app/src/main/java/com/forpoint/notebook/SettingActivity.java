package com.forpoint.notebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingActivity extends AppCompatActivity {
    public final static  int REQUEST_CODE=3;
    public static  int RESULT_CODE_OK=1;
    public static  int RESULT_CODE_CANCEL=0;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(SettingActivity.RESULT_CODE_CANCEL);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
