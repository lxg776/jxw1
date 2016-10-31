package com.xiwang.jxw.biz;

import com.loopj.android.http.RequestParams;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.config.ServerConfig;

/**
 * @author liangxg
 * @description 发布逻辑
 * @date 2015/11/23
 * @modifier
 */
public class PublishBiz {


    /**
     * 发布求职
     * @param jobs 职位，多个用逗号隔开
     * @param company 单位/公司名称
     * @param ability 能力要求
     * @param duty 岗位职责,
     * @param experience 经验要求
     * @param wages 待遇,
     * @param workaddress 工作地址
     * @param number 招聘人数
     * @param cname 联系人
     * @param mobile 联系电话
     * @param title 标题
     * @param content 联系电话
     * @param handle
     */
    public static void publishEmploy(String jobs,String company,String ability,String duty,String experience,String wages,String workaddress,String number,String cname,String mobile,String title,String content,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("action","new");
        params.put("type","jobs");
        params.put("a","posts");
        params.put("jobs",jobs);
        params.put("company",company);
        params.put("ability",ability);
        params.put("duty",duty);
        params.put("experience",experience);
        params.put("wages",wages);
        params.put("workaddress",workaddress);
        params.put("number",number);
        params.put("cname",cname);
        params.put("mobile",mobile);
        params.put("title",title);
        params.put("content",content);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
               // BaseBean.setResponseObjectList(responseBean, DigUserBean.class, "diglist");
                handle.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });
    }


    /**
     *
     * @param cname 姓名
     * @param birthdate 生日
     * @param sex 0:保密 1女 2男
     * @param edu 学历
     * @param experience 经验
     * @param mobile 手机
     * @param position 职位
     * @param wages 期望工资
     * @param resume 自我评价
     * @param handle 回调
     */
    public static void publishQiuzhi(String cname,String birthdate,String sex,String edu,String experience,String mobile,String position,String wages,String resume,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("action","new");
        params.put("type","qjobs");
        params.put("a","posts");
        params.put("cname",cname);
        params.put("birthdate",birthdate);
        params.put("sex",sex);
        params.put("edu",edu);
        params.put("experience",experience);
        params.put("mobile",mobile);
        params.put("position",position);
        params.put("wages",wages);
        params.put("resume",resume);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                // BaseBean.setResponseObjectList(responseBean, DigUserBean.class, "diglist");
                handle.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });

    }


    /**
     * 发布招聘求职
     * @param jobs
     * @param company
     * @param ability
     * @param duty
     * @param experience
     * @param wages
     * @param workaddress
     * @param number
     * @param cname
     * @param mobile
     * @param content
     * @param handle
     */
    public static void publishEmploy(String jobs,String company,String ability,String duty,String experience,String wages,String workaddress,String number,String cname,String mobile,String content,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("action","new");
        params.put("type","jobs");
        params.put("a","posts");
        params.put("jobs",jobs);
        params.put("company",company);
        params.put("ability",ability);
        params.put("duty",duty);
        params.put("experience",experience);
        params.put("wages",wages);
        params.put("workaddress",workaddress);
        params.put("number",number);
        params.put("cname",cname);
        params.put("mobile",mobile);
        params.put("content",content);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                // BaseBean.setResponseObjectList(responseBean, DigUserBean.class, "diglist");
                handle.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });

    }


    /**
     * 发布房屋出租/出售
     * @param type 出租/出售 {出租：zhire 出售: hire  求租：qhire}
     * @param htype 户型
     * @param msize 面积
     * @param ymoney 价格
     * @param subject 标题
     * @param content 描述
     * @param cname 联系人
     * @param mobile 手机号
     * @param aids 图片
     * @param handle 回调
     */
    public  static void publicHouse(String type,String htype,String msize,String ymoney,String subject,String content,String cname,String mobile,String address,String aids,final BaseBiz.RequestHandle handle){
        String url=ServerConfig.GETAPP_URL;
        RequestParams params =new RequestParams();
        params.put("action","new");
        params.put("a","posts");
        params.put("type",type);
        params.put("msize",msize);
        params.put("ymoney",ymoney);
        params.put("address",address);
        params.put("subject",subject);
        params.put("content",content);
        params.put("cname",cname);
        params.put("mobile",mobile);
        params.put("aids",aids);

        BaseBiz.getRequest(url, params, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                // BaseBean.setResponseObjectList(responseBean, DigUserBean.class, "diglist");
                handle.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                handle.onFail(responseBean);
            }

            @Override
            public ResponseBean getRequestCache() {
                return handle.getRequestCache();
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                handle.onRequestCache(result);
            }
        });
    }









}
