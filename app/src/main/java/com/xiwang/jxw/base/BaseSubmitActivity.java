package com.xiwang.jxw.base;

/**
 * Created by sunshine on 15/12/20.
 */
public abstract class BaseSubmitActivity extends BaseActivity{
    /**
     * 校验输入
     * @return
     */
    protected  abstract boolean checkInput();

    /**
     * 提交方法
     * @return
     */
    protected  void  submit(){
        if(!checkInput()){
            return;
        }
    };


}
