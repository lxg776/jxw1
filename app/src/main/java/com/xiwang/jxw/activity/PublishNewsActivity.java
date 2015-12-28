package com.xiwang.jxw.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseSubmitActivity;
import com.xiwang.jxw.widget.MyInputEditView;
import com.xiwang.jxw.widget.MyTextSelectView;
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
    EditText content_edt;
    /** 图片上传*/
    UploadImgView uploadView;
    /** 富媒体*/
    private RichEditor mEditor;

    @Override
    protected boolean checkInput() {
        return false;
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

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_publish_news;
    }

    @Override
    protected void findViews() {
        type_select_tv= (MyTextSelectView) findViewById(R.id.type_select_tv);
        title_edt= (MyInputEditView) findViewById(R.id.title_edt);
        content_edt= (EditText) findViewById(R.id.content_edt);
        uploadView= (UploadImgView) findViewById(R.id.uploadView);
        title_edt.setVisibility(View.GONE);
        content_edt.setVisibility(View.GONE);
        type_select_tv.setVisibility(View.GONE);



        mEditor= (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        //mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");



        uploadView.setPickImageListener(new UploadImgView.PickImageListener() {
            @Override
            public void onImageSelect(List<String> picklist) {
                if(null!=picklist&&picklist.size()>0){
                    for(String url:picklist){
                        mEditor.insertImage(url,"tag");
                    }
                }
            }
        });
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
}
