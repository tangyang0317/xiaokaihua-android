package com.xkh.hzp.xkh;

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

import com.xkh.hzp.xkh.fragment.FragmentFactory;

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
        indexRadioGroup = findViewById(R.id.indexRadioGroup);
        contentBgLayout = findViewById(R.id.contentBgLayout);
        if (contentBgLayout.getForeground() == null) {
            contentBgLayout.setForeground(new ColorDrawable(Color.parseColor("#ffffff")));
        }
        contentBgLayout.getForeground().setAlpha(0);
        initFragment(0);
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
