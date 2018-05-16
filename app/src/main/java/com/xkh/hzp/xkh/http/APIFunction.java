package com.xkh.hzp.xkh.http;

import com.xkh.hzp.xkh.BannerBean;
import com.xkh.hzp.xkh.http.base.BaseEntity;
import com.xkh.hzp.xkh.http.config.URlConfig;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tangyang on 18/4/21.
 */

public interface APIFunction {

    @GET(URlConfig.banner)
    Observable<BaseEntity<List<BannerBean>>> getBanner(@Query("type") String type);

    @GET(URlConfig.getToken)
    Call<ResponseBody> getToken(@Query("type") String type);


}
