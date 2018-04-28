package com.xkh.hzp.xkh.http;

import com.xkh.hzp.xkh.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xkh.hzp.xkh.com.BuildConfig;

/**
 * RetrofitHttp请求类
 * SingleTon
 * 初始化OkHttp，RetroFit
 * Created by tangyang on 18/4/21.
 */

public class RetrofitHttp {

    private static RetrofitHttp mRetrofitFactory;
    private static APIFunction mAPIFunction;

    public RetrofitHttp() {
        initClient();
    }


    private void initClient() {
        OkHttpClient.Builder mOkHttpClient = new OkHttpClient.Builder();
        mOkHttpClient.connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS);
        mOkHttpClient.readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS);
        mOkHttpClient.writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient.addInterceptor(loggingInterceptor);
        }

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient.build())
                .build();
        mAPIFunction = mRetrofit.create(APIFunction.class);
    }


    public static RetrofitHttp getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitHttp.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitHttp();
            }

        }
        return mRetrofitFactory;
    }

    public APIFunction API() {
        return mAPIFunction;
    }


}
