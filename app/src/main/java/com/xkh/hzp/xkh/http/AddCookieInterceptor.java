package com.xkh.hzp.xkh.http;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * 获取cookie的拦截器
 * Created by tangyang on 18/4/19.
 */

public class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) SharedprefrenceHelper.getIns(Global.app).readObject("cookie");
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
