package com.xiwang.jxw.activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.bean.ThreadTypeBean;
import com.xiwang.jxw.bean.UploadImgBean;
import com.xiwang.jxw.bean.postbean.TopicBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.util.keyboardUtil;
import com.xiwang.jxw.widget.AutoRelativeLayout;
import com.xiwang.jxw.widget.DeleteEditText;
import com.xiwang.jxw.widget.EmojiView;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.RichEditText;
import com.xiwang.jxw.widget.UploadImgView;

import java.util.ArrayList;
import java.util.List;


/**
 * 发帖界面
 * Created by sunshine on 15/12/22.
 */
public class PublishNewsActivity extends BaseSubmitActivity{

    /** 选择类型*/
    MyTextSelectView type_select_tv;
    /** 标题*/
    DeleteEditText title_edt;
    /** 内容*/
    RichEditText content_edt;
    /** 图片上传uploadview*/
    UploadImgView uploadView;
    TopicBean topicBean;

    /**表情控件*/
    EmojiView emoji_view;
    /**键盘弹出标识*/
    boolean keyBoardFla=false;
    AutoRelativeLayout auto_rl;
    /**主题分类*/
    ArrayList<ThreadTypeBean> threadTypeList;
    /**当前选中的主题*/
    ThreadTypeBean currentThreadType;

    @Override
    protected boolean checkInput() {
        String type=null;
        String fid="";
        if(null!=currentThreadType){
            fid=currentThreadType.getFid();
        }
        String title=title_edt.getText().toString();
        String content=content_edt.getRichText().toString();
        List<UploadImgBean> uploadString=uploadView.getUploadImageUrlList();
        if(CheckUtil.isSelect(context,"分类",fid)){
            return false;
        }
        if(CheckUtil.isEmpty(context, "主题", title)){
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
       // topicBean.setType(type);
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
    protected String getPageName() {
        return "发帖a";
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
        title_edt= (DeleteEditText) findViewById(R.id.title_edt);
        uploadView= (UploadImgView) findViewById(R.id.uploadView);
        content_edt= (RichEditText) findViewById(R.id.content_edt);
       // title_edt.setVisibility(View.GONE);
       // content_edt.setVisibility(View.GONE);
        //type_select_tv.setVisibility(View.GONE);
        emoji_view= (EmojiView) findViewById(R.id.emoji_view);
        auto_rl= (AutoRelativeLayout) findViewById(R.id.auto_rl);


    }

    @Override
    protected void init() {
        initTypeSelectTv(CommonUtil.getThreadTypeList(context),type_select_tv);
        UserBiz.testCookies();

    }

    /**
     * 初始化弹出分类
     * @param threadTypeList
     * @param tv
     */
    private void initTypeSelectTv( final ArrayList<ThreadTypeBean> threadTypeList, MyTextSelectView tv){
        type_select_tv.setTitleText("选择分类");
        if(threadTypeList!=null&&threadTypeList.size()>0){
            String showItem[]=new String[threadTypeList.size()];
            for(int i=0;i<showItem.length;i++){
                showItem[i]=threadTypeList.get(i).getName();
            }
            tv.setShowItemes(showItem);
            tv.setOnItemClickListener(new MyTextSelectView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    currentThreadType=threadTypeList.get(postion);
                }
            });
        }
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
//        content_edt.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    if (auto_rl.isKeyBoard) {
//                        emoji_view.onKeyBoard();
//                    }
//                } else {
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//        });

        emoji_view.setEmojiListener(new EmojiView.EmojiListener() {
            @Override
            public void onClickEmojiView(SmileBean bean) {
                if(bean.isDeleteSimile()){
                    content_edt.deleteEmoji();
                }else{
                    content_edt.addEmoji(bean);
                }
            }

            @Override
            public void onClickPictureView() {

            }

            @Override
            public void onEmojiShow() {
                onEShow();
            }

            @Override
            public void onKeyBoard() {
                if(!auto_rl.isKeyBoard){
                    keyboardUtil.showKeyBoard(context, content_edt);
                }
            }
        });
//        uploadView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onEmojiHide();
//            }
//        });


        auto_rl.setKeyboardListener(new AutoRelativeLayout.OnKeyBoardListener() {
            @Override
            public void onKeyboardShow(int keyBoardHeight) {
                    if(content_edt.hasFocus()&&emoji_view.getVisibility()==View.GONE) {
                        onKeyShow();
                    }else if(!content_edt.hasFocus()){
                        onEmojiHide();
                    }else if(content_edt.hasFocus()&&emoji_view.content_ll.getVisibility()==View.VISIBLE){
                        onKeyShow();
                    }
            }

            @Override
            public void onKeyBoardHide() {
                    if(emoji_view.getVisibility()==View.VISIBLE&&emoji_view.content_ll.getVisibility()==View.GONE){
                            onEmojiHide();
                    }
            }
        });
    }

    /**
     *  显示表情
     */
    private void onEShow(){


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.setVisibility(View.VISIBLE);
                type_select_tv.setVisibility(View.GONE);
                title_edt.setVisibility(View.GONE);
                uploadView.setVisibility(View.VISIBLE);
            }
        },150);

        keyboardUtil.hideKeyBoard(context, content_edt);
    }

    /**
     * 监听返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if(emoji_view.isContentFla()){
                onEmojiHide();
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }




    /**
     *  显示键盘
     */
    private void onKeyShow(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.setVisibility(View.VISIBLE);
                emoji_view.onKeyBoard();
                type_select_tv.setVisibility(View.GONE);
                title_edt.setVisibility(View.GONE);
                uploadView.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    /**
     * 隐藏表情以及键盘
     */
    private void  onEmojiHide(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.onHide();
                emoji_view.setVisibility(View.GONE);
                type_select_tv.setVisibility(View.VISIBLE);
                title_edt.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    /**
     * 提交数据
     */
    @Override
    protected void submit() {
        if(!checkInput()){
            return;
        }
        NewsBiz.publishTopic(PublishNewsActivity.this,topicBean, new BaseBiz.RequestHandle() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
