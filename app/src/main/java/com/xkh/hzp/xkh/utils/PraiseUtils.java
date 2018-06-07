package com.xkh.hzp.xkh.utils;

import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.HashMap;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName PraiseUtils
 * @Author tangyang
 * @DATE 2018/6/5
 **/
public class PraiseUtils {

    public static PraiseUtils getIns() {
        return new PraiseUtils();
    }

    public void praise(String dynamicId, String userId, final PraisedCallback praisedCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("dynamicId", dynamicId);
        params.put("userId", userId);
        ABHttp.getIns().post(UrlConfig.dynamciPraised, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public boolean onFailure(String code, String msg) {
                praisedCallback.onFail();
                return true;
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                praisedCallback.notLogin();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    praisedCallback.onPraise();
                }
            }
        });
    }

    public void unPraise(String dynamicId, String userId, final UnPraisedCallback praisedCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("dynamicId", dynamicId);
        params.put("userId", userId);
        ABHttp.getIns().post(UrlConfig.dynamciUnPraised, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public boolean onFailure(String code, String msg) {
                praisedCallback.onFail();
                return true;
            }


            @Override
            public void onNotLogin() {
                super.onNotLogin();
                praisedCallback.notLogin();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    praisedCallback.onUnPraise();
                }
            }
        });
    }


    public interface PraisedCallback {
        void onPraise();

        void onFail();

        void notLogin();
    }

    public interface UnPraisedCallback {

        void onFail();

        void notLogin();

        void onUnPraise();
    }

}
