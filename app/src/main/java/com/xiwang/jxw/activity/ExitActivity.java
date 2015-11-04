package com.xiwang.jxw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * ×÷Õß: liangxg on 2015/8/28.
 */
public class ExitActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }

    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        finish();
        //ÍË³ö
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
        }
    }
}
