package com.xiwang.jxw.widget;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseView;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.bean.SmileListBean;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
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
    /** 下标*/
    List<View> indexView=new ArrayList<View>();
    /**回调*/
    EmojiListener listener;


    public EmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public EmojiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
      //init();
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
        emoji_iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                content_ll.setVisibility(View.VISIBLE);
                keyboard_iv.setVisibility(View.VISIBLE);
                emoji_iv.setVisibility(View.GONE);
                if(null!=listener){
                    listener.onEmojiShow();
                }
            }
        });
        keyboard_iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                content_ll.setVisibility(View.GONE);
                keyboard_iv.setVisibility(View.GONE);
                emoji_iv.setVisibility(View.VISIBLE);
                if(null!=listener){
                    listener.onKeyBoard();
                }
            }
        });
        /**
         * 监听页面
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    if(position<=page_index_ll.getChildCount()-1){
                        for(int i=0;i<page_index_ll.getChildCount();i++){
                            if(i==position){
                                page_index_ll.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.pink700_circle));
                            }else{
                                page_index_ll.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.gray500_circle));
                            }
                        }
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void init() {
        smileListBeans= (ArrayList<SmileListBean>) SpUtil.getObject(context, getResources().getString(R.string.cache_smiles));
        initChangeButton(smileListBeans);


    }

    /**
     * 初始化改变按钮
     * @param smileList
     */
    private void initChangeButton(ArrayList<SmileListBean> smileList){
        if(null==smileList||smileList.size()==0){
            return;
        }
        if(change_ll.getChildCount()>0){
            change_ll.removeAllViews();
        }
        for(int i=0;i<smileList.size();i++){
            SmileListBean smileListBean=smileList.get(i);
            final TextView tv_btn= (TextView) View.inflate(context,R.layout.d_changebtn,null);
            tv_btn.setText(smileListBean.getName());


            change_ll.addView(tv_btn);
            /**页面显示准备布局*/
            ReadyShowData showData=new ReadyShowData();
            List<List<SmileBean>> pageList=initSmilePage(smileListBean);
            List<View> viewList=new ArrayList<>();
            List<View> indexViewList=new ArrayList<>();
            for(int pageIndex=0;pageIndex<pageList.size();pageIndex++){
                View pageView=View.inflate(context,R.layout.d_emoji_gv,null);
                initSimlesPage(pageView,pageList.get(pageIndex));
                viewList.add(pageView);
                indexViewList.add(getIndexView());
            }
            EmojiPageAdapter pageAdapter=new EmojiPageAdapter(viewList);
            showData.pagerAdapter=pageAdapter;
            showData.indexViewList=indexViewList;
            showData.tag=smileListBean.getName();
            tv_btn.setTag(showData);
            tv_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener(tv_btn);
                }
            });
        }
        if(change_ll.getChildCount()>0){
            clickListener((TextView)change_ll.getChildAt(0));
        }
    }

    /**
     * 点击事件
     * @param tv_btn
     */
    private void clickListener( TextView tv_btn){
                    ReadyShowData showData= (ReadyShowData) tv_btn.getTag();
                    viewPager.setAdapter(showData.pagerAdapter);
                    if(page_index_ll.getChildCount()>0){
                        page_index_ll.removeAllViews();
                    }
                    for(int index=0;index<showData.indexViewList.size();index++){
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DisplayUtil.dip2px(context,10),DisplayUtil.dip2px(context,10));
                        params.rightMargin=DisplayUtil.dip2px(context,16);
                        showData.indexViewList.get(index).setBackgroundDrawable(getResources().getDrawable(R.drawable.gray500_circle));
                        page_index_ll.addView(showData.indexViewList.get(index),params);
                    }
        /**
         * 改变按钮颜色
         */
        for(int index=0;index<change_ll.getChildCount();index++){
            View view=change_ll.getChildAt(index);
            if(view instanceof TextView){
                TextView tv=(TextView) view;
                if(tv.equals(tv_btn)){
                    tv.setTextColor(getResources().getColor(R.color.orange_700));
                }else{
                    tv.setTextColor(getResources().getColor(R.color.gray_500));
                }
            }
        }



        if(page_index_ll.getChildCount()>0){
            page_index_ll.getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.pink700_circle));
        }
    }


    /**
     * 获取下标圈圈视图
     * @return
     */
    private View getIndexView(){
        View view=new View(context);
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DisplayUtil.dip2px(context,16),DisplayUtil.dip2px(context,16));
       // params.rightMargin=DisplayUtil.dip2px(context,16);
        //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_circle));
        return  view;
    }

    /**
     * 初始化表情页面
     * @param page
     * @param listData
     */
    private void initSimlesPage(View page,List<SmileBean> listData){

        AutoGridView gridView= (AutoGridView) page.findViewById(R.id.grid_view);
        SmileAdapter adapter=new SmileAdapter(listData);
        gridView.setAdapter(adapter);

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
                if(i!=0&&i%20==0){

                    SmileBean del=new SmileBean();
                    del.setIsDeleteSimile(true);
                    itemPage.add(del);
                    pageList.add(itemPage);
                    /*
                     清空重新记录
                     */
                    itemPage=new ArrayList<>();
                    itemPage.add(smileBean);
                }else{
                    itemPage.add(smileBean);
                }
            }
        }
        return pageList;
    }




    /**
     * 表情页适配器
     */
    class EmojiPageAdapter extends PagerAdapter{
        List<View> pageViews;
        @Override
        public int getCount() {
            if(null!=pageViews){
                return pageViews.size();
            }
            return 0;
        }

        public EmojiPageAdapter( List<View> pageViews){
            this.pageViews=pageViews;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pageViews.get(position);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }






    /**
     * 表情适配器
     */
    class SmileAdapter extends BaseAdapter{
        List<SmileBean> listSmiles;

       public SmileAdapter(List<SmileBean> list){
            this.listSmiles=list;
        }

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
           final SmileBean smileBean=getItem(position);
            if(convertView==null){
                convertView=View.inflate(context,R.layout.item_emoji_view,null);
            }
           final ImageView img_iv= (ImageView) convertView.findViewById(R.id.img_iv);
            if(smileBean.isDeleteSimile()){
                convertView.setBackgroundResource(R.mipmap.ic_launcher);
                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null!=listener){
                            listener.onClickEmojiView(smileBean);
                        }
                    }
                });

            }else{
                ImgLoadUtil.getInstance().displayImage(smileBean.getImg(), img_iv, ImgLoadUtil.defaultDisplayOptions, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        img_iv.setImageDrawable(getResources().getDrawable(R.mipmap.trans));
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        img_iv.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null!=listener){
                            listener.onClickEmojiView(smileBean);
                        }
                    }
                });
            }
            return convertView;
        }
    }

    /**
     * 准备显示数据
     */
    class ReadyShowData{
            /** 页面适配器*/
            PagerAdapter pagerAdapter;
             /** 下标视图*/
            List<View> indexViewList;
            /** 当前的tag*/
            String tag;
    }

    /**
     * 回调监听
     */
   public interface  EmojiListener{
            /**点击表情view*/
            public void onClickEmojiView(SmileBean bean);
            /**表情显示*/
            public void onEmojiShow();
            /**点击键盘*/
            public void onKeyBoard();
    }

}
