package com.xiwang.jxw.widget;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseView;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.util.SpUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签view
 * Created by liangxg on 2016/1/15.
 */
public class EmojiView extends BaseView {
    /** 表情，键盘切换*/
    ImageView emoji_iv,keyboard_iv;
    /** 表情内容*/
    LinearLayout content_ll;
    /** 表情vp*/
    ViewPager viewPager;
    /** 更改下标的布局*/
    LinearLayout page_index_ll;

    LinearLayout change_ll;

    /** 表情数据*/
    ArrayList<SmileListBean> smileListBeans;


    public EmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public EmojiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_emoji;
    }

    @Override
    protected void findViews() {
        emoji_iv= (ImageView) view_Parent.findViewById(R.id.emoji_iv);
        keyboard_iv= (ImageView) view_Parent.findViewById(R.id.keyboard_iv);
        content_ll= (LinearLayout) view_Parent.findViewById(R.id.content_ll);
        viewPager= (ViewPager) view_Parent.findViewById(R.id.viewPager);
        page_index_ll= (LinearLayout) view_Parent.findViewById(R.id.page_index_ll);
        change_ll= (LinearLayout) view_Parent.findViewById(R.id.change_ll);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        smileListBeans= (ArrayList<SmileListBean>) SpUtil.getObject(context, getResources().getString(R.string.cache_smiles));


    }

    /**
     * 初始化改变按钮
     * @param smileList
     */
    private void initChangeButton(ArrayList<SmileListBean> smileList){
        if(null==smileList||smileList.size()==0){
            return;
        }
        for(int i=0;i<smileList.size();i++){
            SmileListBean smileListBean=smileList.get(i);
            TextView tv_btn= (TextView) View.inflate(context,R.layout.d_changebtn,null);
            tv_btn.setText(smileListBean.getName());
            change_ll.addView(tv_btn);
            tv_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    /**
     * 初始化表情页面
     * @param smileListBean
     */
    private void initSimlesPage(SmileListBean smileListBean){

    }


    @Override
    protected void onDestroyView(){

    }

    /**
     * 初始化表情数据 让其按每页20个表情分布
     * @param smileListBean
     * @return
     */
    private List<List<SmileBean>> initSmilePage(SmileListBean smileListBean){
        List<List<SmileBean>> pageList=new ArrayList<>();
        if(null!=smileListBean.getList()){
            List<SmileBean> itemPage=new ArrayList<>();
            for(int i=0;i<smileListBean.getList().size();i++){
                SmileBean smileBean=smileListBean.getList().get(i);
                if(i!=0&&i%19==0){
                    itemPage.add(smileBean);
                    SmileBean del=new SmileBean();
                    del.setIsDeleteSimile(true);
                    itemPage.add(del);
                    pageList.add(itemPage);
                    /*
                     清空重新记录
                     */
                    itemPage=new ArrayList<>();
                }else{
                    itemPage.add(smileBean);
                }
            }
        }
        return pageList;
    }


    class SmileAdapter extends BaseAdapter{
        List<SmileBean> listSmiles;

        @Override
        public int getCount() {
            if(null!=listSmiles){
                return  listSmiles.size();
            }
            return 0;
        }

        @Override
        public SmileBean getItem(int position) {
            return listSmiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){

            }


            return null;
        }
    }

}
