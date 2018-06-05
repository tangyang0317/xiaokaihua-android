package com.xkh.hzp.xkh.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.SearchPagerAdapter;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SearchActivity
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView editText;
    private PagerSlidingTabStrip mTab;
    private ViewPager mVp;
    private TextView cancel;
    private SearchPagerAdapter adapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        hideToolbar();
        editText = findViewById(R.id.activity_search_btn_edit);
        editText.setOnClickListener(this);
        mTab = findViewById(R.id.activity_search_tabLayout);
        mVp = findViewById(R.id.activity_search_viewPager);
        cancel = findViewById(R.id.activity_search_cancel);
        cancel.setOnClickListener(this);
        mTab.setVisibility(View.VISIBLE);
        mVp.setVisibility(View.VISIBLE);
        editText.setText(getIntent().getStringExtra("content"));
        init();

    }

    private void init() {
        adapter = new SearchPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        mVp.setCurrentItem(getIntent().getIntExtra("index", 0));
        mTab.setViewPager(mVp);
        adapter.setData(getIntent().getStringExtra("content"));
    }

    @Override
    public void setListenner() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_cancel:
                finish();
                break;
            case R.id.activity_search_btn_edit:
                finish();
                break;
        }
    }
}
