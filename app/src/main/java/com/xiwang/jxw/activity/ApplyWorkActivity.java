package com.xiwang.jxw.activity;

import android.view.View;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.UploadImgesAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.widget.MyDatePickView;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.UploadImgView;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by sunshine on 15/12/7.
 */
public class ApplyWorkActivity extends BaseActivity{
    MyDatePickView datePick;
    MyTextSelectView xinzi_select;
    UploadImgesAdapter uploadImgesAdapter;
    String tag="ApplyWorkActivity";
    UploadImgView uploadVIew;

    private RichEditor mEditor;

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apple_work;
    }

    @Override
    protected void findViews() {
        datePick= (MyDatePickView) findViewById(R.id.datePick);
        datePick.setFragmentManager(getFragmentManager());
        //uploadVIew= (UploadImgView) findViewById(R.id.uploadVIew);
        xinzi_select= (MyTextSelectView) findViewById(R.id.xinzi_select);
        String showItemes[]={"2000~3000","3000~5000","6000以上"};
        xinzi_select.setShowItemes(showItemes);
        mEditor= (RichEditor) findViewById(R.id.editor);
    }



    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");


            }
        });

    }


}
