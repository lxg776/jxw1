package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.TextAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DialogUtil;
import com.xiwang.jxw.util.ImgLoadUtil;

/**
 * 个人信息activity
 * Created by liangxg on 2016/2/23.
 */
public class PersionDetailActivity extends BaseActivity implements  View.OnClickListener {
    /**用户头像*/
    ImageView user_headimg_iv;
    /**昵称*/
    TextView name_tv;
    /**性别*/
    TextView sex_tv;
    /**邮箱*/
    TextView email_tv;
    /**手机号码*/
    TextView cellphone_tv;
    /**个性签名*/
    TextView sign_tv;

    /**性别按钮*/
    RelativeLayout sex_rl;
    /**邮箱按钮*/
    RelativeLayout email_rl;
    /**个性签名*/
    LinearLayout sign_rl;



    @Override
    protected String getPageName() {
        return "个人信息a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_persion_detail;
    }

    @Override
    protected void findViews() {
        name_tv= (TextView) findViewById(R.id.name_tv);
        user_headimg_iv= (ImageView) findViewById(R.id.user_headimg_iv);
        sex_tv= (TextView) findViewById(R.id.sex_tv);
        email_tv= (TextView) findViewById(R.id.email_tv);
        cellphone_tv= (TextView) findViewById(R.id.cellphone_tv);
        sign_tv= (TextView) findViewById(R.id.sign_tv);
        sex_rl= (RelativeLayout) findViewById(R.id.sex_rl);
        email_rl= (RelativeLayout) findViewById(R.id.email_rl);
        sign_rl= (LinearLayout) findViewById(R.id.sign_rl);
    }

    @Override
    protected void init() {
        showUserInfo();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        sex_rl.setOnClickListener(this);
        sign_rl.setOnClickListener(this);
        email_rl.setOnClickListener(this);
    }

    /**
     * 显示个人数据
     */
    private void showUserInfo(){
        UserBean userBean= UserBiz.getUserBean(context);
        if(null!=userBean){
            ImgLoadUtil.displayImage(CommonUtil.getAboutAbsoluteImgUrl(userBean.getFace()), user_headimg_iv);
            name_tv.setText(userBean.getUsername());
            sex_tv.setText(userBean.getSex());
            if(null!=userBean.getUserInfoBean()){
                sign_tv.setText(userBean.getUserInfoBean().getSignature());
                //cellphone_tv.setText(userBean.getUserInfoBean().getUsername());
            }
        }
    }


    /**
     * 弹出性别选择
     */
    private void showSexListView(){
        TextAdapter textAdapter=new TextAdapter(context);
        final  String [] showItemes=getResources().getStringArray(R.array.select_sex);
        textAdapter.setTextArray(showItemes);
        String titleText="性别";
        DialogUtil.defaultDialogListView(context, textAdapter, titleText, new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sex_tv.setText(showItemes[position]);
            }
        }, new DialogUtil.DialogLinstener() {
            @Override
            public void onShow() {

            }

            @Override
            public void onDismiss() {

            }
        });
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){

                case R.id.sex_rl:
                    /*
                        性别按钮
                         */
                    showSexListView();
                    break;
                case R.id.email_rl:
                       /*
                        邮箱按钮
                         */
                    Intent intent=new Intent(context,PersonMsgSaveActivity.class);
                    intent.putExtra(IntentConfig.SEND_TYPE,PersonMsgSaveActivity.TYPE_EMAIL);
                    intent.putExtra(IntentConfig.SEND_VALUE,email_tv.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.sign_rl:
                         /*
                        个性签名按钮
                         */
                    Intent signIntent=new Intent(context,PersonMsgSaveActivity.class);
                    signIntent.putExtra(IntentConfig.SEND_TYPE,PersonMsgSaveActivity.TYPE_SIGN);
                    signIntent.putExtra(IntentConfig.SEND_VALUE,sign_tv.getText().toString());
                    startActivity(signIntent);
                    break;
            }
    }
}
