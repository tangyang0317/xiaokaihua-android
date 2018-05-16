package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xkh.hzp.xkh.R;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * 商务合作
 *
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName BusinessCooperationActivity
 * @Author tangyang
 * @DATE 2018/5/16
 **/
public class BusinessCooperationActivity extends BaseActivity implements View.OnClickListener {

    private EditText companyNameEdit, linkManNameEdit, linkManPhoneEdit, promotionRequireEdit;
    private Button businessConperationSubmitBtn;
    private String companyNameStr, linkManNameStr, linkManPhoneStr, promotionRequireStr;

    @Override
    public int getLayoutId() {
        return R.layout.activity_business_cooperation;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("商务合作");
        companyNameEdit = findViewById(R.id.companyNameEdit);
        linkManNameEdit = findViewById(R.id.linkManNameEdit);
        linkManPhoneEdit = findViewById(R.id.linkManPhoneEdit);
        promotionRequireEdit = findViewById(R.id.promotionRequireEdit);
        businessConperationSubmitBtn = findViewById(R.id.businessConperationSubmitBtn);
    }

    @Override
    public void setListenner() {
        businessConperationSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == businessConperationSubmitBtn) {
            companyNameStr = companyNameEdit.getText().toString();
            linkManNameStr = linkManNameEdit.getText().toString();
            linkManPhoneStr = linkManPhoneEdit.getText().toString();
            promotionRequireStr = promotionRequireEdit.getText().toString();

            if (TextUtils.isEmpty(companyNameStr)) {
                Toasty.warning(this, "请填写公司名称").show();
                return;
            }
            if (TextUtils.isEmpty(linkManNameStr)) {
                Toasty.warning(this, "请填写联系人姓名").show();
                return;
            }
            if (TextUtils.isEmpty(linkManPhoneStr)) {
                Toasty.warning(this, "请填写联系方式").show();
                return;
            }
            if (TextUtils.isEmpty(promotionRequireStr)) {
                Toasty.warning(this, "请填写招聘需求").show();
                return;
            }

        }
    }
}
