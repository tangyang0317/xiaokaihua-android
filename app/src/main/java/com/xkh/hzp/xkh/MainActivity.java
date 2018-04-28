package com.xkh.hzp.xkh;

import android.util.Log;

import com.umeng.onlineconfig.OnlineConfigAgent;
import com.xkh.hzp.xkh.http.RetrofitHttp;
import com.xkh.hzp.xkh.http.base.BaseEntity;
import com.xkh.hzp.xkh.http.base.BaseObserver;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import xkh.hzp.xkh.com.BannerBean;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        String update = OnlineConfigAgent.getInstance().getConfigParams(this, "update");
        Log.d("xkh", update);

        RetrofitHttp.getInstence().API().getBanner("index").compose(this.<BaseEntity<List<BannerBean>>>setThread()).subscribe(new BaseObserver<List<BannerBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<BannerBean>> t) throws Exception {
                Log.d("xkh", t.getResult().get(0).getImgUrl());
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }
        });


        RetrofitHttp.getInstence().API().getStringBanner("index").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("xkh", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    @Override
    public void setListenner() {

    }
}
