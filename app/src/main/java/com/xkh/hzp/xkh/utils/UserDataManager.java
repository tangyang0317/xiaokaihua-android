package com.xkh.hzp.xkh.utils;

import android.content.Context;

import com.xkh.hzp.xkh.entity.WebUserBean;

import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName UserDataManager
 * @Author tangyang
 * @DATE 2018/5/24
 **/
public class UserDataManager {

    public static UserDataManager userDataManager;

    public static UserDataManager getInstance() {
        if (userDataManager == null) {
            userDataManager = new UserDataManager();
        }
        return userDataManager;
    }

    /***
     * 读取登陆用户信息
     * @return
     */
    public WebUserBean getLoginUser() {
        return (WebUserBean) SharedprefrenceHelper.getIns(Global.app).readObject("loginUser");
    }

    /***
     * 保存登陆用户信息
     * @param webUserBean
     */
    public void putLoginUser(WebUserBean webUserBean) {
        SharedprefrenceHelper.getIns(Global.app).saveObject("loginUser", webUserBean);
    }


}
