package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.xkh.hzp.xkh.R;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.adapter.GridViewAddImgesAdpter;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName JoinTalentActivity
 * @Author tangyang
 * @DATE 2018/5/10
 **/
public class JoinTalentActivity extends BaseActivity implements View.OnClickListener {

    private Button joinUsSubmitBtn;
    private EditText talentLinkManNameEdit, talentLinkManPhoneEdit, talentWeiBoEdit, talentHeightEdit, talentWidghtEdit;
    private String talentLinkManNameStr, talentLinkManPhoneStr, talentWeiBoStr, talentHeightStr, talentWidghtStr;
    private GridView joinTalentGridView;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_join_talent;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("加入达人");
        joinTalentGridView = findViewById(R.id.joinTalentGridView);
        talentLinkManNameEdit = findViewById(R.id.talentLinkManNameEdit);
        talentLinkManPhoneEdit = findViewById(R.id.talentLinkManPhoneEdit);
        talentWeiBoEdit = findViewById(R.id.talentWeiBoEdit);
        talentHeightEdit = findViewById(R.id.talentHeightEdit);
        talentWidghtEdit = findViewById(R.id.talentWidghtEdit);
        joinUsSubmitBtn = findViewById(R.id.joinUsSubmitBtn);

        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(null, this, 3);
        gridViewAddImgesAdpter.setMaxImages(5);
        joinTalentGridView.setAdapter(gridViewAddImgesAdpter);

    }

    @Override
    public void setListenner() {
        joinUsSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == joinUsSubmitBtn) {
            talentLinkManNameStr = talentLinkManNameEdit.getText().toString();
            talentLinkManPhoneStr = talentLinkManPhoneEdit.getText().toString();
            talentWeiBoStr = talentWeiBoEdit.getText().toString();
            talentHeightStr = talentHeightEdit.getText().toString();
            talentWidghtStr = talentWidghtEdit.getText().toString();
            if (TextUtils.isEmpty(talentLinkManNameStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写联系人姓名").show();
                return;
            }
            if (TextUtils.isEmpty(talentLinkManPhoneStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写联系方式").show();
                return;
            }
            if (TextUtils.isEmpty(talentWeiBoStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写微博账号").show();
                return;
            }
            if (TextUtils.isEmpty(talentHeightStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写身高").show();
                return;
            }
            if (TextUtils.isEmpty(talentWidghtStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写体重").show();
                return;
            }


        }
    }
}
