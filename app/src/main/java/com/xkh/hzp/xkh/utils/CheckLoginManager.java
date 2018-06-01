package com.xkh.hzp.xkh.utils;

import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.HashMap;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName CheckLoginManager
 * @Author tangyang
 * @DATE 2018/6/1
 **/
public class CheckLoginManager {

    public static CheckLoginManager checkLoginManager;


    public static CheckLoginManager getInstance() {
        if (checkLoginManager == null) {
            checkLoginManager = new CheckLoginManager();
        }
        return checkLoginManager;
    }


    public void isLogin(final CheckLoginCallBack checkLoginCallBack) {
        ABHttp.getIns().post(UrlConfig.loginCheck, null, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    checkLoginCallBack.isLogin(result);
                }
            }
        });
    }

    public interface CheckLoginCallBack {

        void isLogin(boolean isLogin);
    }


}
