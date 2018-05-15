package com.xkh.hzp.xkh.activity;

import android.widget.GridView;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.adapter.GridViewAddImgesAdpter;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName JoinTalentActivity
 * @Author tangyang
 * @DATE 2018/5/10
 **/
public class JoinTalentActivity extends BaseActivity {

    private GridView joinTalentGridView;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_join_talent;
    }

    @Override
    public void initView() {
        joinTalentGridView = findViewById(R.id.joinTalentGridView);

        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(null, this, 3);
        gridViewAddImgesAdpter.setMaxImages(5);
        joinTalentGridView.setAdapter(gridViewAddImgesAdpter);

    }

    @Override
    public void setListenner() {

    }
}
