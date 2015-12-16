package com.xiwang.jxw.activity;

import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.UploadImgesAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.event.PickImageEvent;
import com.xiwang.jxw.widget.MyDatePickView;
import com.xiwang.jxw.widget.MyTextSelectView;
import com.xiwang.jxw.widget.UploadImgGridView;

/**
 * Created by sunshine on 15/12/7.
 */
public class ApplyWorkActivity extends BaseActivity{
    MyDatePickView datePick;
    MyTextSelectView xinzi_select;
    UploadImgesAdapter uploadImgesAdapter;
    String tag="ApplyWorkActivity";
    UploadImgGridView uploadVIew;

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
        uploadVIew= (UploadImgGridView) findViewById(R.id.uploadVIew);
        xinzi_select= (MyTextSelectView) findViewById(R.id.xinzi_select);
        String showItemes[]={"2000~3000","3000~5000","6000以上"};
        xinzi_select.setShowItemes(showItemes);
        uploadImgesAdapter =new UploadImgesAdapter(context,tag);
        uploadVIew.setAdapter(uploadImgesAdapter);
    }

    @Override
    protected boolean useEventBus() {
        return true;
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

    public void onEvent(PickImageEvent event){
        if(tag.equals(event.fromTag)){
            if(null!=event.picklist&&event.picklist.size()>0){
                uploadImgesAdapter.imageModelList.addAll(0, event.picklist);
                uploadImgesAdapter.notifyDataSetChanged();
            }
        }
    }
}
