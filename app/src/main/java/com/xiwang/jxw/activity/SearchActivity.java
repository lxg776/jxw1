package com.xiwang.jxw.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.HistorySearchAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.DeleteEditText;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 *  搜索帖子
 *  Created by liangxg on 2016/12/20.
 */
public class SearchActivity extends BaseActivity{

    DeleteEditText mInputKeyEdt;//输入的关键词
    LinearLayout mHistoryKeyLL; //历史搜索的布局
    TagFlowLayout mHotSearchTag; //热门搜索
    HistorySearchAdapter mHistorySearchAdapter;//历史搜索适配器
    ArrayList<String> historySearchData;//历史搜索记录

    TextView mDoSearchBtn;//查询操作


    BGARefreshLayout mReslutListview; //查询结果列表



    @Override
    protected String getPageName() {
        return "搜索界面";
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void findViews() {
        mHotSearchTag = (TagFlowLayout) findViewById(R.id.hot_search_tag);
        mHistoryKeyLL = (LinearLayout) findViewById(R.id.history_key_ll);
        mInputKeyEdt = (DeleteEditText) findViewById(R.id.input_key_edt);
        mDoSearchBtn = (TextView) findViewById(R.id.do_search_btn);

        mReslutListview = (BGARefreshLayout) findViewById(R.id.reslut_listview);


    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

        Object o =  SpUtil.getObject(context,getString(R.string.search_data));
        //搜索列表
        if(o!=null&& o instanceof  List){
            historySearchData = (ArrayList<String>) o;
        }
        if(historySearchData==null){
            historySearchData = new ArrayList<String>();
        }
        mHistorySearchAdapter = new HistorySearchAdapter(context);
        mHistorySearchAdapter.setHistorySearchData(historySearchData);


    }

    @Override
    protected void widgetListener() {
        //返回按钮
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //清空按钮
        findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historySearchData.clear();
                SpUtil.setObject(context,getString(R.string.search_data),historySearchData);
                mHistorySearchAdapter.notifyDataSetChanged();
            }
        });
        //搜索方法
        mDoSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKey = mInputKeyEdt.getText().toString();//搜索的关键词
                if(TextUtils.isEmpty(searchKey)){
                    ToastUtil.showToast(context,"搜索内容不能为空");
                    return;
                }


            }
        });
    }

    /**
     * 搜索的方法
     * @param keyWord
     */
    private  void doSearch(String keyWord){
        //保存记录
        historySearchData.add(keyWord);
        SpUtil.setObject(context,getString(R.string.search_data),historySearchData);
        //更新显示
        mHistorySearchAdapter.notifyDataSetChanged();

    }


    //显示搜索选项
    private void showOptionView(){

    }
}
