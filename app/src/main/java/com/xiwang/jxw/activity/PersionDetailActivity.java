package com.xiwang.jxw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.TextAdapter;
import com.xiwang.jxw.base.BaseActivity;

import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.ShowImg;
import com.xiwang.jxw.bean.UploadImgBean;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.biz.SystemBiz;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.event.FinishEvent;
import com.xiwang.jxw.event.PickHeadImageEvent;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DialogUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.PercentView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

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
    /**上传图片*/
    LinearLayout uploadhead_ll;
    /**绑定手机号*/
    RelativeLayout cellphone_rl;
    /**绑定手机号*/
    PercentView user_headimg_pv;

    UserBean userBean;






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
    protected boolean useEventBus() {
        return  true;
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
        uploadhead_ll= (LinearLayout) findViewById(R.id.uploadhead_ll);
        cellphone_rl= (RelativeLayout) findViewById(R.id.cellphone_rl);
        user_headimg_pv= (PercentView) findViewById(R.id.user_headimg_pv);



    }

    @Override
    protected void init() {

        setActivityTag("PersionDetailActivity");
        showUserInfo();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        sex_rl.setOnClickListener(this);
        sign_rl.setOnClickListener(this);
        uploadhead_ll.setOnClickListener(this);
        email_rl.setOnClickListener(this);
        cellphone_rl.setOnClickListener(this);
    }

    /**
     * 显示个人数据
     */
    private void showUserInfo(){
        userBean= UserBiz.getUserBean(context);
        if(null!=userBean){
            if(null!=userBean.getUserInfoBean()&&!TextUtils.isEmpty(userBean.getUserInfoBean().getFace())){
                ImgLoadUtil.displayImage(userBean.getUserInfoBean().getFace(), user_headimg_iv);
            }
            name_tv.setText(userBean.getUsername());

            if("1".equals(userBean.getSex())){
                userBean.setSex("男");
            }else if("0".equals(userBean.getSex())){
                userBean.setSex("未知");
            }else if("2".equals(userBean.getSex())){
                userBean.setSex("女");
            }


            sex_tv.setText(userBean.getSex());
            if(null!=userBean.getUserInfoBean()){
                sign_tv.setText(userBean.getUserInfoBean().getSignature());
                //cellphone_tv.setText(userBean.getUserInfoBean().getUsername());
            }
        }
        if(null!=userBean.getUserInfoBean()){
            if(TextUtils.isEmpty(userBean.getUserInfoBean().getCellphone())){
                cellphone_tv.setTextColor(getResources().getColor(R.color.red_500));
                cellphone_tv.setText("立即绑定");
            }else{
                cellphone_tv.setTextColor(getResources().getColor(R.color.black_transparent_54));
                cellphone_tv.setText(userBean.getUserInfoBean().getCellphone());
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
                case R.id.cellphone_rl:
                    if(TextUtils.isEmpty(userBean.getUserInfoBean().getCellphone())){
                        /**
                         * 跳转去绑定手机
                         */
                        IntentUtil.gotoActivity(context,WritePhoneActivity.class);

                    }


                    break;
                case R.id.uploadhead_ll:
                     /*
                        上传头像
                         */
                    Intent headIntent=new Intent(context,PickHeadImageActivity.class);
                    headIntent.putExtra(getString(R.string.send_tag),activityTag);
                    startActivity(headIntent);
                    break;

            }
    }

    public void onEvent(UserInfoEvent event){
        if (event.tag.equals(activityTag)){
           showUserInfo();
        }
    }


    public void onEvent(PickHeadImageEvent event){
        if (event.tag.equals(activityTag)){
            uploadHeadImage(event.path);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    private void uploadHeadImage(String path){

        Uri fileUri=Uri.fromFile(new File(path));
        Bitmap bitmap=getBitmapFromUri(fileUri);
        if(bitmap!=null){
            user_headimg_iv.setImageBitmap(bitmap);
        }
        try {
            UserBiz.uploadHeadImg(path, new SystemBiz.UploadImgListener() {
                @Override
                public void onSuccess(ResponseBean responseBean) {
                    user_headimg_pv.setVisibility(View.INVISIBLE);
                    String imgUrl= (String) responseBean.getObject();
                    /**清除本地缓存*/
                    removeHeadImg(imgUrl);
                    /**更新用户数据*/
                    UserBiz.getMyInfo(PersionDetailActivity.this,null,true);
                }

                @Override
                public void onFail(ResponseBean responseBean) {
                    user_headimg_pv.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onProgress(final int progress) {
                    if(user_headimg_pv.getVisibility()!=View.VISIBLE){
                        user_headimg_pv.setVisibility(View.VISIBLE);
                    }
                    user_headimg_pv.setPercent(progress);
                }

                @Override
                public void onFinish() {
                    user_headimg_pv.setVisibility(View.INVISIBLE);
                }
            });
        } catch (FileNotFoundException e) {
            user_headimg_pv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 清除缓存数据
     * @param url
     */
    private void removeHeadImg(String url){
        Iterator<String> it = ImgLoadUtil.getInstance().getMemoryCache().keys().iterator();
        while (it.hasNext()) {
            String allUrl = it.next();
          if(allUrl.contains(url)){
              ImgLoadUtil.getInstance().getMemoryCache().remove(allUrl);
              ImgLoadUtil.getInstance().getDiskCache().remove(allUrl);


          }
        }


    }





}

