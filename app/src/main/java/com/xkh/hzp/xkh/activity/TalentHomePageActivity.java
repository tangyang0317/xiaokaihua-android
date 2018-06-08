package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.fragment.FragmentFactory;
import com.xkh.hzp.xkh.fragment.HomePageFragment;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentHomePageActivity
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class TalentHomePageActivity extends BaseActivity {

    public static void lanuchActivity(Activity activity, String talentUserId) {
        Intent intent = new Intent(activity, TalentHomePageActivity.class);
        intent.putExtra("talentUserId", talentUserId);
        activity.startActivity(intent);
    }

    public String getTalentUserId() {
        return getIntent().getStringExtra("talentUserId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void initView() {
        hideToolbar();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = HomePageFragment.getInstance(getTalentUserId());
        transaction.replace(R.id.homePageContainerLayout, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void setListenner() {

    }
}
