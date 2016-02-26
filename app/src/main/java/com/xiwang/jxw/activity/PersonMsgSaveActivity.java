package com.xiwang.jxw.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.config.IntentConfig;

/**
 * Created by liangxg on 2016/2/23.
 */
public class PersonMsgSaveActivity extends BaseSubmitActivity{

    //修改邮箱
    public static String TYPE_EMAIL="typeMail";
    //修改签名
    public static String TYPE_SIGN="typeSign";


    EditText input_msg;
    /**保存类型*/
    String saveType;
    /**保存的值*/
    String value;

    @Override
    protected String getPageName() {
        return "修改个人信息a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("个人信息");
        if(TYPE_EMAIL.equals(saveType)){
            /*
                保存邮箱
             */
            toolbar.setTitle("邮箱地址");
        }else if(TYPE_SIGN.equals(saveType)){
            toolbar.setTitle("个性签名");
        }
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_save) {
                    submit();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 保存按钮
     */
    @Override
    protected void submit() {
        String msg=input_msg.getText().toString();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_msg_save;
    }

    @Override
    protected void findViews() {
        input_msg= (EditText) findViewById(R.id.input_msg);
    }

    @Override
    protected void init() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    protected void initGetData() {
        saveType=getIntent().getStringExtra(IntentConfig.SEND_TYPE);
        value=getIntent().getStringExtra(IntentConfig.SEND_VALUE);
        input_msg.setText(value);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected boolean checkInput() {
        return false;
    }
}
