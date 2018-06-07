package com.xkh.hzp.xkh.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OtherRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import xkh.hzp.xkh.com.base.Global;

/**
 * Http工具类
 * 支持GET POST，POSTJSON
 * <p>
 * Created by tangyang on 18/4/17.
 */

public class ABHttp {

    private static ABHttp abHttpIns;
    public static final String CODE_SUCCESS = "200";
    public static final String CODE_NOTLOGIN = "ul_530";
    public static final String TAG = ABHttp.class.getName();

    private ABHttp() {

    }

    public static ABHttp getIns() {
        if (abHttpIns == null) {
            abHttpIns = new ABHttp();
        }
        return abHttpIns;

    }


    /***
     * get请求
     * @param url      请求地址
     * @param params   参数集合
     * @param httpCallback 回调
     */
    public void get(final String url, HashMap<String, String> params, final AbHttpCallback httpCallback) {
        if (!isConnected()) {
            Logger.d("网络连接断开，请检查网络");
            Toasty.error(Global.app, "网络连接断开，请检查网络").show();
        }
        try {
            GetBuilder builder = OkHttpUtils.get().url(url);
            if (params != null) {
                for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    builder.addParams(entry.getKey(), entry.getValue());
                }
                Logger.d("开始get请求:" + params.toString());
            }
            builder.build().execute(new Callback<AbHttpEntity>() {

                @Override
                public void onBefore(Request request, int id) {
                    httpCallback.onStart();
                }

                @Override
                public AbHttpEntity parseNetworkResponse(Response response, int id) throws Exception {
                    String str = response.body().string();
                    Logger.d("AbHttpAO.get() + url(" + url + ") 成功 <--结果:" + response.toString() + "\n结果:(" + str + ")");
                    AbHttpEntity entity = new AbHttpEntity(str);
                    httpCallback.setupEntity(entity);
                    entity.parseFields(str);
                    return entity;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.d("AbHttpAO.get() + url(" + url + ")  失败 XXX 结果:" + e.toString());
                    Toast.makeText(Global.app, "网络访问失败, 请稍候重试", Toast.LENGTH_SHORT).show();
                    httpCallback.onFinish();
                }

                @Override
                public void onResponse(AbHttpEntity response, int id) {
                    if (!httpCallback.onGetString(response.getStr())) {
                        if (ABHttp.CODE_SUCCESS.equals(response.getCode())) {
                            try {
                                httpCallback.onSuccessGetObject(response.getCode(), response.getMsg(), response.isSuccess(), response.getExtras());
                            } catch (Exception e) {
                                httpCallback.onFailure(null, "服务端异常!");
                                Logger.d("解析与处理返回数据异常!");
                            }
                        } else if (ABHttp.CODE_NOTLOGIN.equals(response.getCode())) {
                            httpCallback.onNotLogin();
                        }
                        httpCallback.onFinish();
                    }
                }
            });
        } catch (Exception e) {
            Logger.d("网络请求异常:" + e.getMessage().toString());
        }


    }


    /***
     * get请求
     * @param url      请求地址
     * @param params   参数集合
     * @param httpCallback 回调
     */
    public void restfulGet(final String url, LinkedHashMap<String, String> params, final AbHttpCallback httpCallback) {
        if (!isConnected()) {
            Logger.d("网络连接断开，请检查网络");
            Toasty.error(Global.app, "网络连接断开，请检查网络").show();
        }
        try {
            StringBuilder paramsBuilder = new StringBuilder(url);
            if (params != null) {
                for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    paramsBuilder.append("/" + entry.getValue());
                }
                Logger.d("开始get请求:" + paramsBuilder.toString());
            }
            GetBuilder builder = OkHttpUtils.get().url(paramsBuilder.toString());
            builder.build().execute(new Callback<AbHttpEntity>() {

                @Override
                public void onBefore(Request request, int id) {
                    httpCallback.onStart();
                }

                @Override
                public AbHttpEntity parseNetworkResponse(Response response, int id) throws Exception {
                    String str = response.body().string();
                    Logger.d("AbHttpAO.get() + url(" + url + ") 成功 <--结果:" + response.toString() + "\n结果:(" + str + ")");
                    AbHttpEntity entity = new AbHttpEntity(str);
                    httpCallback.setupEntity(entity);
                    entity.parseFields(str);
                    return entity;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.d("AbHttpAO.get() + url(" + url + ")  失败 XXX 结果:" + e.toString());
                    Toast.makeText(Global.app, "网络访问失败, 请稍候重试", Toast.LENGTH_SHORT).show();
                    httpCallback.onFinish();
                }

                @Override
                public void onResponse(AbHttpEntity response, int id) {
                    if (!httpCallback.onGetString(response.getStr())) {
                        if (ABHttp.CODE_SUCCESS.equals(response.getCode())) {
                            try {
                                httpCallback.onSuccessGetObject(response.getCode(), response.getMsg(), response.isSuccess(), response.getExtras());
                            } catch (Exception e) {
                                httpCallback.onFailure(null, "服务端异常!");
                                Logger.d("解析与处理返回数据异常!");
                            }
                        } else if (ABHttp.CODE_NOTLOGIN.equals(response.getCode())) {
                            httpCallback.onNotLogin();
                        }
                        httpCallback.onFinish();
                    }
                }
            });
        } catch (Exception e) {
            Logger.d("网络请求异常:" + e.getMessage().toString());
        }


    }

    /***
     * get请求
     * @param url      请求地址
     * @param params   参数集合
     * @param httpCallback 回调
     */
    public void restfulPost(final String url, LinkedHashMap<String, String> params, final AbHttpCallback httpCallback) {
        if (!isConnected()) {
            Logger.d("网络连接断开，请检查网络");
            Toasty.error(Global.app, "网络连接断开，请检查网络").show();
        }
        try {
            StringBuilder paramsBuilder = new StringBuilder(url);
            if (params != null) {
                for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    paramsBuilder.append("/" + entry.getValue());
                }
                Logger.d("开始restPost请求:" + paramsBuilder.toString());
            }
            PostFormBuilder builder = OkHttpUtils.post().url(paramsBuilder.toString());
            builder.build().execute(new Callback<AbHttpEntity>() {

                @Override
                public void onBefore(Request request, int id) {
                    httpCallback.onStart();
                }

                @Override
                public AbHttpEntity parseNetworkResponse(Response response, int id) throws Exception {
                    String str = response.body().string();
                    Logger.d("AbHttpAO.restPost() + url(" + url + ") 成功 <--结果:" + response.toString() + "\n结果:(" + str + ")");
                    AbHttpEntity entity = new AbHttpEntity(str);
                    httpCallback.setupEntity(entity);
                    entity.parseFields(str);
                    return entity;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.d("AbHttpAO.restPost() + url(" + url + ")  失败 XXX 结果:" + e.toString());
                    Toast.makeText(Global.app, "网络访问失败, 请稍候重试", Toast.LENGTH_SHORT).show();
                    httpCallback.onFinish();
                }

                @Override
                public void onResponse(AbHttpEntity response, int id) {
                    if (!httpCallback.onGetString(response.getStr())) {
                        if (ABHttp.CODE_SUCCESS.equals(response.getCode())) {
                            try {
                                httpCallback.onSuccessGetObject(response.getCode(), response.getMsg(), response.isSuccess(), response.getExtras());
                            } catch (Exception e) {
                                httpCallback.onFailure(null, "服务端异常!");
                                Logger.d("解析与处理返回数据异常!");
                            }
                        } else if (ABHttp.CODE_NOTLOGIN.equals(response.getCode())) {
                            httpCallback.onNotLogin();
                        }
                        httpCallback.onFinish();
                    }
                }
            });
        } catch (Exception e) {
            Logger.d("网络请求异常:" + e.getMessage().toString());
        }


    }


    /***
     * post请求
     * @param url  请求地址
     * @param params    参数集合
     * @param httpCallback  请求回调
     */
    public void post(final String url, Map params, final AbHttpCallback httpCallback) {
        if (!isConnected()) {
            Logger.d("网络连接断开，请检查网络");
            Toasty.error(Global.app, "网络连接断开，请检查网络");
        }
        Logger.d("开始post请求:" + url);
        try {
            PostFormBuilder builder = OkHttpUtils.post().url(url);
            if (params != null) {
                for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    builder.addParams(entry.getKey(), entry.getValue());
                }
            }
            builder.build().execute(new Callback<AbHttpEntity>() {

                @Override
                public void onBefore(Request request, int id) {
                    httpCallback.onStart();
                }

                @Override
                public AbHttpEntity parseNetworkResponse(Response response, int id) throws Exception {
                    String str = response.body().string();
                    Logger.d("AbHttpAO.post() + url(" + url + ")成功 <--结果:" + response.toString() + "\n结果:(" + str + ")");
                    AbHttpEntity entity = new AbHttpEntity(str);
                    httpCallback.setupEntity(entity);
                    entity.parseFields(str);
                    return entity;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.d("AbHttpAO.post() + url(" + url + ") 失败 XXX 结果:" + e.toString());
                    Toast.makeText(Global.app, "网络访问失败, 请稍候重试", Toast.LENGTH_SHORT).show();
                    httpCallback.onFinish();
                }

                @Override
                public void onResponse(AbHttpEntity response, int id) {
                    if (!httpCallback.onGetString(response.getStr())) {
                        if (ABHttp.CODE_SUCCESS.equals(response.getCode())) {
                            try {
                                httpCallback.onSuccessGetObject(response.getCode(), response.getMsg(), response.isSuccess(), response.getExtras());
                            } catch (Exception e) {
                                httpCallback.onFailure(null, "服务端异常!");
                                Logger.d("解析与处理返回数据异常!");
                            }
                        } else if (ABHttp.CODE_NOTLOGIN.equals(response.getCode())) {
                            httpCallback.onNotLogin();
                        }
                        httpCallback.onFinish();
                    }
                }
            });
        } catch (Exception e) {
            Logger.d("网络请求异常:" + e.getMessage().toString());
        }
    }

    /***
     * postJson请求
     *
     * @param url   请求url
     * @param json  json数据
     * @param httpCallback  请求回调
     */
    public void postJSON(final String url, String json, final AbHttpCallback httpCallback) {
        if (!isConnected()) {
            Toasty.error(Global.app, "网络连接断开，请检查网络");
        }
        Logger.d("开始post请求:" + url);
        PostStringBuilder builder = OkHttpUtils.postString().url(url);
        builder.mediaType(MediaType.parse("application/json; charset=utf-8"));
        builder.content(json);
        builder.build().execute(new Callback<AbHttpEntity>() {

            @Override
            public void onBefore(Request request, int id) {
                httpCallback.onStart();
            }

            @Override
            public AbHttpEntity parseNetworkResponse(Response response, int id) throws Exception {
                String str = response.body().string();
                Logger.d("AbHttpAO.postJSON() + url(" + url + ")成功 <--结果:" + response.toString() + "\n结果:(" + str + ")");
                AbHttpEntity entity = new AbHttpEntity(str);
                httpCallback.setupEntity(entity);
                entity.parseFields(str);
                return entity;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.d("AbHttpAO.postJSON() + url(" + url + ") 失败 XXX 结果:" + e.toString());
                Toast.makeText(Global.app, "网络访问失败, 请稍候重试", Toast.LENGTH_SHORT).show();
                httpCallback.onFinish();
            }

            @Override
            public void onResponse(AbHttpEntity response, int id) {
                if (!httpCallback.onGetString(response.getStr())) {
                    if (ABHttp.CODE_SUCCESS.equals(response.getCode())) {
                        try {
                            httpCallback.onSuccessGetObject(response.getCode(), response.getMsg(), response.isSuccess(), response.getExtras());
                        } catch (Exception e) {
                            httpCallback.onFailure(null, "服务端异常!");
                            Logger.d("解析与处理返回数据异常!");
                        }
                    } else if (ABHttp.CODE_NOTLOGIN.equals(response.getCode())) {
                        httpCallback.onNotLogin();
                    }
                    httpCallback.onFinish();
                }
            }
        });
    }

    /**
     * 获取联网状态符
     *
     * @return 状态符
     */
    public int getConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) Global.app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        int netType = -1; //-1
        if (info == null || !info.isConnected()) {
            netType = -1;
        } else {
            netType = info.getType();
        }
        return netType;
    }

    /**
     * 是否已连接.
     *
     * @return
     */
    public boolean isConnected() {
        return getConnectivity() >= 0;
    }
}
