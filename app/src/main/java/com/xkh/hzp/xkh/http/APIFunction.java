package com.xkh.hzp.xkh.http;

import com.xkh.hzp.xkh.BannerBean;
import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.entity.request.LoginParam;
import com.xkh.hzp.xkh.entity.request.RegisterParam;
import com.xkh.hzp.xkh.entity.result.RegisterResultBean;
import com.xkh.hzp.xkh.http.base.BaseEntity;
import com.xkh.hzp.xkh.http.config.URlConfig;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Api方法定义
 * Created by tangyang on 18/4/21.
 */

public interface APIFunction {

    @GET(URlConfig.banner)
    Observable<BaseEntity<List<BannerBean>>> getBanner(@Query("type") String type);

    @GET(URlConfig.getToken)
    Call<ResponseBody> getToken(@Query("type") String type);

    @POST(URlConfig.login)
    Observable<BaseEntity<WebUserBean>> login(@Body LoginParam loginParam);

    @POST(URlConfig.register)
    Observable<BaseEntity<RegisterResultBean>> register(@Body RegisterParam registerParam);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(URlConfig.findPwd)
    Observable<BaseEntity<Boolean>> findPwd(@Body RequestBody requestBody);

    @POST(URlConfig.modifyPwd)
    Observable<BaseEntity<Boolean>> modifyPwd(@QueryMap HashMap<String, String> paramMap);

    @POST(URlConfig.logout)
    Observable<BaseEntity<Boolean>> logout();

    @POST(URlConfig.loginCheck)
    Observable<BaseEntity<String>> loginCheck();

    @GET(URlConfig.getAuthCode)
    Observable<BaseEntity<Boolean>> getAuthCode(@Path("type") String type, @Path("imgAuthCode") String imgAuthCode, @Path("phone") String phone);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(URlConfig.businessCooperation)
    Observable<BaseEntity<Boolean>> businessCooperation(@Body RequestBody requestBody);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(URlConfig.talentJoinUs)
    Observable<BaseEntity<Boolean>> talentJoinUs(@Body RequestBody requestBody);


}
