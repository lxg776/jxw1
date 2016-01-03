package com.xiwang.jxw.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.UploadImgBean;
import com.xiwang.jxw.bean.postbean.TopicBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.MyInputEditView;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.RichEditText;
import com.xiwang.jxw.widget.UploadImgView;

import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * 发帖界面
 * Created by sunshine on 15/12/22.
 */

public class PublishNewsActivity extends BaseSubmitActivity{

    /** 选择类型*/
    MyTextSelectView type_select_tv;
    /** 标题*/
    MyInputEditView title_edt;
    /** 内容*/
    RichEditText content_edt;
    /** 图片上传uploadview*/
    UploadImgView uploadView;
    TopicBean topicBean;


    @Override
    protected boolean checkInput() {
        String type="";
        String title=title_edt.getText().toString();
        String content=content_edt.getRichText().toString();
        List<UploadImgBean> uploadString=uploadView.getUploadImageUrlList();
        String fid="";

        if(CheckUtil.isEmpty(context,"主题",title)){
            return false;
        }
        if(CheckUtil.isEmpty(context,"内容",content)){
            return false;
        }
        /**
         * 组装上传数据
         */

        topicBean=new TopicBean();
        topicBean.setAction("new");
        topicBean.setContent(content);
        topicBean.setFid(fid);
        topicBean.setType("");
        topicBean.setSubject(title);
        if(null!=uploadString&&uploadString.size()>0){
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<uploadString.size();i++){
                sb.append(uploadString.get(i).getAid());
                if(i!=uploadString.size()-1){
                    sb.append(",");
                }
            }
            topicBean.setAids(sb.toString());
        }





        return true;
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发布帖子");
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

                if (item.getItemId() == R.id.action_finish) {
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_publish_news;
    }

    @Override
    protected void findViews() {
        type_select_tv= (MyTextSelectView) findViewById(R.id.type_select_tv);
        title_edt= (MyInputEditView) findViewById(R.id.title_edt);
        uploadView= (UploadImgView) findViewById(R.id.uploadView);
        content_edt= (RichEditText) findViewById(R.id.content_edt);
       // title_edt.setVisibility(View.GONE);
       // content_edt.setVisibility(View.GONE);
        //type_select_tv.setVisibility(View.GONE);



    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    /**
     * 提交数据
     */
    @Override
    protected void submit() {
        if(!checkInput()){
            return;
        }
        NewsBiz.publishTopic(topicBean, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                ToastUtil.showToast(context,responseBean.getInfo());
                finish();
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                ToastUtil.showToast(context,responseBean.getInfo());
            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finlish, menu);
        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_finish) {
//            submit();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
