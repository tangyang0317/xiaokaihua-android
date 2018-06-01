package com.xkh.hzp.xkh;

import android.Manifest;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xkh.hzp.xkh.fragment.FragmentFactory;

import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.AppManager;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup indexRadioGroup;
    private RelativeLayout contentBgLayout;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_fb435b).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        hideToolbar();
        requestPermissions();
        indexRadioGroup = findViewById(R.id.indexRadioGroup);
        contentBgLayout = findViewById(R.id.contentBgLayout);
        if (contentBgLayout.getForeground() == null) {
            contentBgLayout.setForeground(new ColorDrawable(Color.parseColor("#ffffff")));
        }
        contentBgLayout.getForeground().setAlpha(0);
        initFragment(0);
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
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_fb435b).init();
                        initFragment(0);
                        break;
                    case R.id.talentRB:
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_fb435b).init();
                        initFragment(1);
                        break;
                    case R.id.mineRB:
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
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

    @Override
    public void onClick(View view) {
//        if (view == videoImg) {
//
//        } else if (view == imgTextImg) {
//            rightLowerMenu.close(true);
//            PublishPictureTextActvity.lunchActivity(this, null, PublishPictureTextActvity.class);
//        }
    }
}
