package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

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
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
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
            if (TextUtils.isEmpty(linkManPhoneStr) || linkManPhoneStr.length() != 11) {
                Toasty.warning(this, "请填写联系方式").show();
                return;
            }
            if (TextUtils.isEmpty(promotionRequireStr)) {
                Toasty.warning(this, "请填写招聘需求").show();
                return;
            }

            businessCooperation();

        }
    }

    /***
     *
     * 商务合作
     */
    private void businessCooperation() {
        HashMap<String, String> params = new HashMap<>();
        params.put("companyName", companyNameStr);
        params.put("phone", linkManPhoneStr);
        params.put("contactName", linkManNameStr);
        params.put("promotionRequire", promotionRequireStr);
        params.put("userId", UserDataManager.getInstance().getUserId());

        ABHttp.getIns().postJSON(UrlConfig.businessCooperation, JsonUtils.toJson(params), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    BusinessCooperationActivity.this.finish();
                    Toasty.info(BusinessCooperationActivity.this, "提交成功").show();
                } else {
                    Toasty.info(BusinessCooperationActivity.this, msg).show();
                }
            }
        });
    }
}
