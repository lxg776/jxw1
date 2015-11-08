package com.xiwang.jxw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 作者: lxg776 on 2015/8/28.
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
        //退出
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
        }
    }
}
