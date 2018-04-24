package xkh.hzp.xkh.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import xkh.hzp.xkh.com.base.BaseActivty;
import xkh.hzp.xkh.com.http.RetrofitHttp;
import xkh.hzp.xkh.com.http.base.BaseEntity;
import xkh.hzp.xkh.com.http.base.BaseObserver;
import xkh.hzp.xkh.com.http.base.BaseObserverForString;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivty {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
