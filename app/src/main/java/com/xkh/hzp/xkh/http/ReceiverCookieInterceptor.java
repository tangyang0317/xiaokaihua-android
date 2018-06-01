package com.xkh.hzp.xkh.http;

import com.xkh.hzp.xkh.config.UrlConfig;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * 获取cookie的拦截器
 * Created by tangyang on 18/4/19.
 */

public class ReceiverCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (originalResponse.request().url().toString().equals(UrlConfig.register) || originalResponse.request().url().toString().equals(UrlConfig.login)) {
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                SharedprefrenceHelper.getIns(Global.app).remove("cookie");
                HashSet<String> cookies = new HashSet<>();
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }
                SharedprefrenceHelper.getIns(Global.app).saveObject("cookie", cookies);
            }
        }
        return originalResponse;
    }
}
