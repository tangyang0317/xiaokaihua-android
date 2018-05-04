package com.xkh.hzp.xkh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.umeng.onlineconfig.OnlineConfigAgent;
import com.xkh.hzp.xkh.fragment.FragmentFactory;
import com.xkh.hzp.xkh.http.RetrofitHttp;
import com.xkh.hzp.xkh.http.base.BaseEntity;
import com.xkh.hzp.xkh.http.base.BaseObserver;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivity {

    private RadioGroup indexRadioGroup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        hideToolbar();
        indexRadioGroup = findViewById(R.id.indexRadioGroup);
        String update = OnlineConfigAgent.getInstance().getConfigParams(this, "update");
        Log.d("xkh", update);
        initFragment(0);
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

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void setListenner() {
        indexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.dynamicRB:
                        initFragment(0);
                        break;
                    case R.id.talentRB:
                        initFragment(1);
                        break;
                    case R.id.mineRB:
                        initFragment(2);
                        break;
                }
            }
        });
    }


    public void initFragment(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = FragmentFactory.createFragment(i);
        transaction.replace(R.id.mainContainerFrameLayout, fragment);
        transaction.commit();
    }
}
