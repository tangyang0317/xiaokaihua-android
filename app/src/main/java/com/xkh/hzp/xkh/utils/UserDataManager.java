package com.xkh.hzp.xkh.utils;

import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;

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
     * 读取登录用户信息
     * @return
     */
    public WebUserBean getLoginUser() {
        return (WebUserBean) SharedprefrenceHelper.getIns(Global.app).readObject("loginUser");
    }

    /**
     * getuserId
     *
     * @return
     */
    public String getUserId() {
        if (getLoginUser() != null) {
            return String.valueOf(getLoginUser().getUid());
        }
        return "";
    }


    /***
     * 是否设置过密码
     * @return
     */
    public boolean hasPwd() {
        if (getLoginUser() != null) {
            return getLoginUser().isHavePassWord();
        }
        return true;
    }

    /**
     * getUserHeadPic
     *
     * @return
     */
    public String getUserHeadPic() {
        if (getLoginUser() != null) {
            return getUserInfo().getHeadPortrait();
        }
        return "";
    }

    /**
     * getuserId
     *
     * @return
     */
    public String getUserNickName() {
        if (getLoginUser() != null) {
            return getUserInfo().getName();
        }
        return "";
    }

    /***
     * 保存登录用户信息
     * @param webUserBean
     */
    public void putLoginUser(WebUserBean webUserBean) {
        SharedprefrenceHelper.getIns(Global.app).saveObject("loginUser", webUserBean);
    }


    /***
     * 保存当前登录用户信息
     * @param userInfoResult
     */
    public void saveUserInfo(UserInfoResult userInfoResult) {
        SharedprefrenceHelper.getIns(Global.app).saveObject("userInfo", userInfoResult);
    }


    /***
     * 获取当前登录的用户信息
     */
    public UserInfoResult getUserInfo() {
        return (UserInfoResult) SharedprefrenceHelper.getIns(Global.app).readObject("userInfo");
    }

}
