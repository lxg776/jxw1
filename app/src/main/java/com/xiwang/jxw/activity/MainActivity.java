package com.xiwang.jxw.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.fragment.FindFragment;
import com.xiwang.jxw.fragment.HomeFragment;
import com.xiwang.jxw.fragment.MineFragment;
import com.xiwang.jxw.fragment.PublishFragment;
import com.xiwang.jxw.widget.MyRadioView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private FindFragment findFragment;
    /** 发布页面 */
    private PublishFragment publishFragment;
    /** home页面 */
    private HomeFragment homeFragment;

    /** home选项 */
    private MyRadioView main_rv;
    /** 油价走势 */
    private MyRadioView find_rv;
    /** 发布选项 */
    private MyRadioView publish_rl;
    /** 我的择项 */
    private MyRadioView mine_rv;

    /** 当前选择项 */
    private MyRadioView radio_current;

    /** 主页 */
    public final static int FRAGMENT_HOME = 0;
    /** 发现 */
    public final static int FRAGMENT_FIND = 1;
    /** 发布 */
    public final static int FRAGMENT_PUBLISH = 2;
    /** 我的 */
    public final static int FRAGMENT_MINE = 3;

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
    protected void findViews() {
        /** 设置toolbar*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        main_rv=(MyRadioView)findViewById(R.id.main_rv);
        find_rv=(MyRadioView)findViewById(R.id.find_rv);
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
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

        main_rv.setOnClickListener(radioClick);
        find_rv.setOnClickListener(radioClick);
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
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
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
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
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
        findFragment = new FindFragment();
        publishFragment = new PublishFragment();
        mineFragmentFragment = new MineFragment();

        list_Fragments.add(homeFragment);
        list_Fragments.add(findFragment);
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
            transaction.replace(R.id.content_frame, list_Fragments.get(position));
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
            radio_current.setCheck(false);
            radio_current = myRadioView;
            radio_current.setCheck(true);
            switch (v.getId()) {
                case R.id.main_rv:
                    switchView(FRAGMENT_HOME);
                    break;
                case R.id.find_rv:
                    switchView(FRAGMENT_FIND);
                    break;
                case R.id.publish_rl:
                    switchView(FRAGMENT_PUBLISH);
                    break;
                case R.id.mine_rv:
                    switchView(FRAGMENT_MINE);
                    break;
                default:
                    break;
            }
        }
    };




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



}
