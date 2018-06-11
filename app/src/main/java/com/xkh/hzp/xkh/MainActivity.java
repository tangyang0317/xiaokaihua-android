package com.xkh.hzp.xkh;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.fragment.FragmentFactory;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.AppManager;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivity {
    private RadioGroup indexRadioGroup;
    private RefreshUIBoardCastReceiver refreshUIBoardCastReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setTranslucentForImageView(MainActivity.this, 0, null);
    }

    @Override
    public void initView() {
        refreshUIBoardCastReceiver = new RefreshUIBoardCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.LOGIN_ACTION);
        intentFilter.addAction(Config.LOGOUT_ACTION);
        registerReceiver(refreshUIBoardCastReceiver, intentFilter);
        hideToolbar();
        requestPermissions();
        indexRadioGroup = findViewById(R.id.indexRadioGroup);
        initFragment(0);
    }


    /***
     *查询用户信息
     */
    private void getUserInfo() {
        String userId = UserDataManager.getInstance().getUserId();
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("userId", userId);
        ABHttp.getIns().restfulGet(UrlConfig.queryuserInfo, hashMap, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<UserInfoResult>() {
                }.getType());
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                /***未登陆或者单点登陆，账号被挤掉****/
                SharedprefrenceHelper.getIns(MainActivity.this).clear();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                refreshUI();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    final UserInfoResult userInfoResult = (UserInfoResult) extra.get("result");
                    if (userInfoResult != null) {
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                    }
                }
            }
        });
    }

    /***
     * 刷新UI页面
     */
    private void refreshUI() {
        if (indexRadioGroup.getCheckedRadioButtonId() == R.id.mineRB) {
            if ("talent".equals(UserDataManager.getInstance().getUserInfo().getUserType())) {
                initFragment(3);
            } else {
                initFragment(2);
            }
        }
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Logger.d(permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Logger.d(permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Logger.d(permission.name + " is denied.");
                        }
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
                } else {
                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                    AppManager.getAppManager().AppExit(this);
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
                        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
                        if (userInfoResult != null && "talent".equals(userInfoResult.getUserType())) {
                            initFragment(3);
                        } else {
                            initFragment(2);
                        }
                        break;
                }
            }
        });


    }

    /***
     * 接收登陆和登出的广播
     */
    public class RefreshUIBoardCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (Config.LOGIN_ACTION.equals(intent.getAction())) {
                    getUserInfo();
                } else if (Config.LOGOUT_ACTION.equals(intent.getAction())) {
                    if (indexRadioGroup.getCheckedRadioButtonId() == R.id.mineRB) {
                        initFragment(2);
                    }
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshUIBoardCastReceiver);
    }

    public void initFragment(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = FragmentFactory.createFragment(i);
        transaction.replace(R.id.mainContainerFrameLayout, fragment);
        transaction.commitAllowingStateLoss();
    }
}
