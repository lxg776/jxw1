package com.xiwang.jxw.activity;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.PushNewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.event.LoginEvent;
import com.xiwang.jxw.event.MenuEvent;
import com.xiwang.jxw.event.PushMessageEvent;
import com.xiwang.jxw.fragment.FindFragment;
import com.xiwang.jxw.fragment.HomeFragment;
import com.xiwang.jxw.fragment.MineFragment;
import com.xiwang.jxw.fragment.PublishFragment;
import com.xiwang.jxw.intf.PushDialogListener;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.DialogUtil;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.NotifyHelper;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.MyRadioView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * @author liangxg
 * @description 主界面
 * @date 2015/11/3
 * @modifier
 */
public class MainActivity extends BaseActivity {
    /** 工具栏*/
    Toolbar toolbar;
    /** fragment模块集合 */
    private List<Fragment> list_Fragments = new ArrayList<Fragment>();
    /** 我的页面 */
    private MineFragment mineFragmentFragment;
    /** 发现页面 */
   // private FindFragment findFragment;
    /** 发布页面 */
    private PublishFragment publishFragment;
    /** home页面 */
    private HomeFragment homeFragment;

    /** home选项 */
    private MyRadioView main_rv;
    /** 发现 */
   // private MyRadioView find_rv;
    /** 发布选项 */
    private MyRadioView publish_rl;
    /** 我的择项 */
    private MyRadioView mine_rv;

    /** 当前选择项 */
    private MyRadioView radio_current;

    /** 主页 */
    public final static int FRAGMENT_HOME = 0;
    /** 发现 */
    //public final static int FRAGMENT_FIND = 1;
    /** 发布 */
    public final static int FRAGMENT_PUBLISH = 1;
    /** 我的 */
    public final static int FRAGMENT_MINE = 2;

    public final static int ACTION_FABU = 4;


    /** 当前显示的Fragment */
    private int current_Fragment = -1;

    /** 退出应用计时器 */
    private Timer timer = null;
    private TimerTask timeTask = null;
    private boolean isExit = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void findViews() {
        /** 设置toolbar*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.mipmap.logo_white);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        main_rv=(MyRadioView)findViewById(R.id.main_rv);
      //  find_rv=(MyRadioView)findViewById(R.id.find_rv);
        publish_rl=(MyRadioView)findViewById(R.id.publish_rl);
        mine_rv=(MyRadioView)findViewById(R.id.mine_rv);





    }

    @Override
    protected void init() {
        timer=new Timer();

        initFragment();
        main_rv.setCheck(true);
        radio_current = main_rv;
        switchView(FRAGMENT_HOME);



        /** 设置表情列表*/
        CommonUtil.setSmileList(context);

        /** 進行自動登錄*/
       UserBiz.autoLogin(context);


    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

        main_rv.setOnClickListener(radioClick);
        //find_rv.setOnClickListener(radioClick);
        publish_rl.setOnClickListener(radioClick);
        mine_rv.setOnClickListener(radioClick);

    }


    public void onSort(MenuItem item) {
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fatie) {
            fatie();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Bundle getBundle(int send_do){
        Bundle bundle=new Bundle();
        bundle.putString(IntentConfig.SEND_FRAMGE_TAG, "");
        bundle.putInt(IntentConfig.SEND_DO,send_do);
        return  bundle;
    }

    /**
     * 发帖
     */
    private void fatie(){
        if(UserBiz.isLogin(MainActivity.this,getBundle(ACTION_FABU))){
            IntentUtil.gotoActivity(context, PublishNewsActivity.class);
        }
    }


    /**
     * 隐藏所有Fragment
     *
     * @version 1.0
     * @createTime 2015-8-12,下午3:20:32
     * @updateTime 2015-8-12,下午3:20:32
     * @createAuthor XiaoHuan
     * @updateAuthor
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
            // myOrderFragment.onPause();
        }
//        if (findFragment != null) {
//            transaction.hide(findFragment);
//        }
        if (publishFragment != null) {
            transaction.hide(publishFragment);
        }
        if (mineFragmentFragment != null) {
            transaction.hide(mineFragmentFragment);
        }
    }

    /**
     *
     * 初始化子模块
     *
     * @version 1.0
     * @createTime 2015年8月19日,下午1:46:34
     * @updateTime 2015年8月19日,下午1:46:34
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     */
    private void initFragment() {

        homeFragment = new HomeFragment();
        //findFragment = new FindFragment();
        publishFragment = new PublishFragment();


        mineFragmentFragment = new MineFragment();

        list_Fragments.add(homeFragment);
        //list_Fragments.add(findFragment);
        list_Fragments.add(publishFragment);
        list_Fragments.add(mineFragmentFragment);

        switchView(FRAGMENT_HOME);
    }

    /**
     *
     * 选择界面
     *
     * @version 1.0
     * @createTime 2015年8月19日,下午1:46:48
     * @updateTime 2015年8月19日,下午1:46:48
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     * @param position
     */
    public void switchView(int position) {
        try {
            if (current_Fragment == position) {
                return;
            }
            // 获取Fragment的操作对象
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, list_Fragments.get(position), position + "fragment");
            if (current_Fragment != -1) {
                getSupportFragmentManager().popBackStack(position + "", 0);
                transaction.addToBackStack(position + "");// 将上一个Fragment存回栈，生命周期为stop，不销毁
            }
            transaction.commitAllowingStateLoss();// 提交更改
            current_Fragment = position;
//            // 设置导航条
//            int toXDelta = 0;
//            int fromXDelta = 0;
//            fromXDelta = currentPage * view_slider.getWidth();
//            toXDelta = position * view_slider.getWidth();
//            currentPage = position;
//            translateSlider(fromXDelta, toXDelta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 底部按钮点击事件处理 */
    private View.OnClickListener radioClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyRadioView myRadioView = (MyRadioView) v;
            if (myRadioView.isCheck) {
                return;
            }
//            // 判断是否登录,没登录直接跳转到登录界面
//            if (v.getId() == R.id.main_radio_oil_price || v.getId() == R.id.main_radio_mine) {
//                if (TApplication.getUserInfoBean() == null) {
//                    IntentUtil.gotoActivity(MainActivity.this, LoginActivity.class);
//                    return;
//                }
//            }

            switch (v.getId()) {
                case R.id.main_rv:
                    switchView(FRAGMENT_HOME);
                    break;
//                case R.id.find_rv:
//                    switchView(FRAGMENT_FIND);
//                    break;
                case R.id.publish_rl:
                    switchView(FRAGMENT_PUBLISH);
                    break;
                case R.id.mine_rv:
                    if(!toMinePage()){
                        return;
                    };
                    break;
                default:
                    break;
            }

            radio_current.setCheck(false);
            radio_current = myRadioView;
            radio_current.setCheck(true);
        }
    };

    /**
     * 跳转我的界面
     */
    public boolean toMinePage(){
        Bundle bundle=new Bundle();
        bundle.putInt(IntentConfig.SEND_DO,FRAGMENT_MINE);
        if(UserBiz.isLogin(this,bundle)){
            switchView(FRAGMENT_MINE);
            radio_current.setCheck(false);
            radio_current = mine_rv;
            radio_current.setCheck(true);
            return true;
        }else{
            return  false;
        }
    }

    /**
     * 跳转主界面
     */
    public void toMainPage(){
        switchView(FRAGMENT_HOME);
        radio_current.setCheck(false);
        radio_current = main_rv;
        radio_current.setCheck(true);
    }


    @Override
    protected String getPageName() {
        return "主页界面";
    }

    @Override
    protected void initActionBar() {

    }

    /**
     *双击退出应用
     */
    @Override
    public void onBackPressed() {
        if (isExit) {
//            AppManager.getAppManager().finishAllActivity();
//            finish();
            Intent intent=new Intent(MainActivity.this,ExitActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            // 获取PID
        } else {
            isExit = true;
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.press_again_exit),
                    Toast.LENGTH_SHORT).show();
            timeTask = new TimerTask() {

                @Override
                public void run() {
                    isExit = false;
                }
            };
            timer.schedule(timeTask, 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==IntentConfig.LOGIN_CODE){
            if(resultCode==RESULT_OK){
                if(null!=data){
                    Bundle bundle=data.getExtras();
                    /*
                     点击发帖出
                     */
                    String fragmentTag=bundle.getString(IntentConfig.SEND_FRAMGE_TAG,"");
                    int do_=bundle.getInt(IntentConfig.SEND_DO,-1);
                    if(!TextUtils.isEmpty(fragmentTag)){
                        Fragment fragment=getSupportFragmentManager().findFragmentByTag(fragmentTag);
                        if(null!=fragment){
                            fragment.onActivityResult(requestCode,resultCode,data);
                        }
                    }

                     /*
                     我的按钮
                     */
                    else if(do_==FRAGMENT_MINE){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toMinePage();
                            }
                        },200);

                    }else if(do_==ACTION_FABU){
                        fatie();
                    }

                }
            }
        }
         super.onActivityResult(requestCode, resultCode, data);
    }


    public void onEvent(LoginEvent event) {
        if(event.islogin){
            UserBiz.getMyInfo(context,null,true);
        }
    }

    public void onEvent(PushMessageEvent event) {
        if(isfront){
            DialogUtil.showPushNewsDialog(MainActivity.this, event.newsBean, new PushDialogListener() {
                @Override
                public void viewPage() {

                }

                @Override
                public void cancel() {

                }
            });
        }else {

            ColumnBean columnBean = new ColumnBean();
            columnBean.setName("测试推送");
            CommonUtil.notifiNews(context, columnBean, event.newsBean,false);
//            Bundle bundle=new Bundle();
//            NewsBean newsBean=new NewsBean();
//            ColumnBean columnBean=new ColumnBean();
//            bundle.putSerializable(getString(R.string.send_news),newsBean);
//            bundle.putSerializable(getString(R.string.send_column), columnBean);
//            newsBean.setTid(event.newsBean.getTid());
//            columnBean.setName("测试推送");
//
//            NotifyHelper.with(this)
//                    .autoCancel(true)
//                    .when(System.currentTimeMillis())
//                    .defaults(Notification.DEFAULT_LIGHTS)
//                    .title(event.newsBean.getSubject())
//                    .message(event.newsBean.getDesc())
//                    .ticker("New Message")
//
//                    .smallIcon(R.mipmap.ic_launcher)
//                    .largeIcon(R.mipmap.ic_launcher)
//                    .click(NewsDetailActivity.class,bundle)
//                    .show();
        }
    }

}
