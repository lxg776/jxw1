package com.xiwang.jxw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.FeedBackActivity;
import com.xiwang.jxw.activity.MainActivity;
import com.xiwang.jxw.activity.MyPublishActivity;
import com.xiwang.jxw.activity.PersionDetailActivity;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.bean.UserBean;
import com.xiwang.jxw.bean.VersionInfoBean;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.event.RegEvent;
import com.xiwang.jxw.event.UserInfoEvent;
import com.xiwang.jxw.event.VersionEvent;
import com.xiwang.jxw.intf.ConfirmListener;
import com.xiwang.jxw.util.AppUtils;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.ToastUtil;


/**
 * @author liangxg
 * @description 我的
 * 我的发布 我的收藏 提到我的
 * @date 2015/9/24
 * @modifier
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{
    /** 个人资料*/
    private LinearLayout ziliao_ll;
    /** 我的收藏*/
    private LinearLayout collect_ll;
    /** 提到我的*/
    private LinearLayout tidao_ll;
    /** 我的发布*/
    private LinearLayout fabu_ll;
    /** 系统设置*/
    private LinearLayout setting_ll;
    /** 用户头像*/
    private ImageView user_headimg_iv;
    /** 用户昵称*/
    private TextView user_tv;
    /** 个性签名*/
    private TextView sign_tv;
    /** 注销当前帐号*/
    private LinearLayout quit_ll;
    /** 意见反馈*/
    private LinearLayout feedback_ll;

    /**当前版本信息*/
    TextView mCurrentVerionTv;

    @Override
    protected String getPageName() {
        return "我的f";
    }

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_mine, null);
    }

    @Override
    protected void findViews() {
        ziliao_ll= (LinearLayout) view_Parent.findViewById(R.id.ziliao_ll);
        fabu_ll= (LinearLayout) view_Parent.findViewById(R.id.fabu_ll);
        setting_ll= (LinearLayout) view_Parent.findViewById(R.id.setting_ll);
        collect_ll = (LinearLayout) view_Parent.findViewById(R.id.collect_ll);
        tidao_ll = (LinearLayout) view_Parent.findViewById(R.id.tidao_ll);
        feedback_ll= (LinearLayout) view_Parent.findViewById(R.id.feedback_ll);
        sign_tv= (TextView) view_Parent.findViewById(R.id.sign_tv);
        user_tv= (TextView) view_Parent.findViewById(R.id.user_tv);
        user_headimg_iv= (ImageView) view_Parent.findViewById(R.id.user_headimg_iv);
        quit_ll=(LinearLayout)view_Parent.findViewById(R.id.quit_ll);
        mCurrentVerionTv=(TextView)view_Parent.findViewById(R.id.current_verion_tv);
    }

    @Override
    public void initGetData() {
        showUserInfo();
    }

    @Override
    protected void widgetListener() {
        ziliao_ll.setOnClickListener(this);
        fabu_ll.setOnClickListener(this);
        setting_ll.setOnClickListener(this);
        collect_ll.setOnClickListener(this);
        tidao_ll.setOnClickListener(this);
        quit_ll.setOnClickListener(this);
        feedback_ll.setOnClickListener(this);

    }

    @Override
    protected void init() {


        updateVersionInfo();
    }

    /**
     * 更新版本信息
     */
    public  void updateVersionInfo(){
       Object o =  SpUtil.getObject(context,getString(R.string.update_verion));
        if(o!=null&& o instanceof VersionInfoBean){
           final VersionInfoBean versionInfoBean = (VersionInfoBean) o;
            int currentCode =  AppUtils.getAppVersionCode(context);
            if(currentCode<versionInfoBean.getVersioncode()){
                findViewById(R.id.version_ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtil.checkVersion(context,"版本更新",versionInfoBean,true);
                    }
                });
                mCurrentVerionTv.setText("有新版本哦，点击进行版本更新");
                mCurrentVerionTv.setTextColor(getResources().getColor(R.color.red));
            }else{
                mCurrentVerionTv.setText("当前版本为v"+AppUtils.getAppVersionName(getActivity()));
                mCurrentVerionTv.setTextColor(getResources().getColor(R.color.black_transparent_54));
                findViewById(R.id.version_ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(context,"已是最新版本!");
                    }
                });
            }
        }else{
            mCurrentVerionTv.setText(AppUtils.getAppName(getActivity())+"v"+AppUtils.getAppVersionName(getActivity()));
            mCurrentVerionTv.setTextColor(getResources().getColor(R.color.black_transparent_54));
            findViewById(R.id.version_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(context,"已是最新版本!");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                //个人信息
                case R.id.ziliao_ll:
                    Intent intent=new Intent(context,PersionDetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fabu_ll:
                    //我的发布
                    MyPublishActivity.jumpActivity(context);
                    break;
                case R.id.setting_ll:
                    break;
                case R.id.collect_ll:
                    //ToastUtil.showToast(context,"lxg776");
                    break;
                case R.id.feedback_ll:
                    //意见反馈
                    FeedBackActivity.openActivity(getActivity(),new Bundle());
                    break;
                case R.id.tidao_ll:
                    break;
                case R.id.quit_ll:
                    CommonUtil.showExitDialog(context,"提示","注销当前账号？",new ConfirmListener(){

                        @Override
                        public void confirm() {
                            /**退出切换到主界面*/
                            MainActivity activity= (MainActivity) getActivity();
                            activity.switchView(MainActivity.FRAGMENT_HOME);
                            activity.toMainPage();

                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    break;
            }
    }

    public void onEvent(LoginEvent event) {
        showUserInfo();
    }
    public void onEvent(UserInfoEvent event) {
        showUserInfo();
    }
    public void onEvent(RegEvent event) {
        showUserInfo();
    }

    public void onEvent(VersionEvent event) {

        updateVersionInfo();
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 显示用户数据
     */
    private void showUserInfo(){
        UserBean userBean= UserBiz.getUserBean(context);
        if(null!=userBean){
            user_tv.setText(userBean.getUsername());
            if(null!=userBean.getUserInfoBean()){
                ImgLoadUtil.displayImage(userBean.getUserInfoBean().getFace(), user_headimg_iv,ImgLoadUtil.getUserOptions());
                if(!TextUtils.isEmpty(userBean.getUserInfoBean().getSignature())){
                    sign_tv.setText(userBean.getUserInfoBean().getSignature());
                }
            }
        }
    }
}
