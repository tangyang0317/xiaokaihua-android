package com.xkh.hzp.xkh.fragment;

import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseFragment;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class MineTalentFragment extends BaseFragment {
    protected ImmersionBar mImmersionBar;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View contentView) {
        initImmersionBar();
    }


    /***
     * 初始化沉浸式
     */
    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }

    @Override
    public void setListernner() {

    }
}
