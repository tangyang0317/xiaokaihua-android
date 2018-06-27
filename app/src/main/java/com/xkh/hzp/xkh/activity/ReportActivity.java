package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.request.ReportParam;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName ReportActivity
 * @Author tangyang
 * @DATE 2018/6/13
 **/
public class ReportActivity extends BaseActivity implements View.OnClickListener {

    private ImageView reportAdContentImg, reportTrashInfoImg, reportIrregularityImg, reportHostilityImg, reportOtherImg;
    private RelativeLayout reportEditRel;
    private EditText reportContentEdit;
    private Button reportSubmitBtn;
    private String reportTitle;
    private RelativeLayout reportAdContentRel, reportTrashInfoRel, reportIrregularityRel, reportHostilityRel, reportOtherRel;


    public static void lanuchActivity(Activity activity, String type, long dynamicId, long userId) {
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("dynamicId", dynamicId);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }

    private String getType() {
        return getIntent().getStringExtra("type");
    }

    private long getDynamicId() {
        return getIntent().getLongExtra("dynamicId", 0);
    }

    private long getUserId() {
        return getIntent().getLongExtra("userId", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
    }

    @Override
    public void initView() {
        setTitleNavigationIcon(R.drawable.icon_back_black);
        setToolbarTitleTv("举报");
        reportAdContentImg = findViewById(R.id.reportAdContentImg);
        reportTrashInfoImg = findViewById(R.id.reportTrashInfoImg);
        reportIrregularityImg = findViewById(R.id.reportIrregularityImg);
        reportHostilityImg = findViewById(R.id.reportHostilityImg);
        reportOtherImg = findViewById(R.id.reportOtherImg);

        reportAdContentRel = findViewById(R.id.reportAdContentRel);
        reportTrashInfoRel = findViewById(R.id.reportTrashInfoRel);
        reportIrregularityRel = findViewById(R.id.reportIrregularityRel);
        reportHostilityRel = findViewById(R.id.reportHostilityRel);
        reportOtherRel = findViewById(R.id.reportOtherRel);

        reportEditRel = findViewById(R.id.reportEditRel);
        reportContentEdit = findViewById(R.id.reportContentEdit);
        reportSubmitBtn = findViewById(R.id.reportSubmitBtn);

    }

    @Override
    public void setListenner() {
        reportAdContentRel.setOnClickListener(this);
        reportTrashInfoRel.setOnClickListener(this);
        reportIrregularityRel.setOnClickListener(this);
        reportHostilityRel.setOnClickListener(this);
        reportOtherRel.setOnClickListener(this);
        reportSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == reportAdContentRel) {
            reportAdContentImg.setImageResource(R.mipmap.btn_check_selected);
            reportHostilityImg.setImageResource(R.drawable.circle_icon_white);
            reportTrashInfoImg.setImageResource(R.drawable.circle_icon_white);
            reportIrregularityImg.setImageResource(R.drawable.circle_icon_white);
            reportOtherImg.setImageResource(R.drawable.circle_icon_white);
            reportTitle = "广告内容";
        } else if (view == reportTrashInfoRel) {
            reportTrashInfoImg.setImageResource(R.mipmap.btn_check_selected);
            reportAdContentImg.setImageResource(R.drawable.circle_icon_white);
            reportHostilityImg.setImageResource(R.drawable.circle_icon_white);
            reportIrregularityImg.setImageResource(R.drawable.circle_icon_white);
            reportOtherImg.setImageResource(R.drawable.circle_icon_white);
            reportTitle = "垃圾信息";
        } else if (view == reportIrregularityRel) {
            reportIrregularityImg.setImageResource(R.mipmap.btn_check_selected);
            reportHostilityImg.setImageResource(R.drawable.circle_icon_white);
            reportTrashInfoImg.setImageResource(R.drawable.circle_icon_white);
            reportAdContentImg.setImageResource(R.drawable.circle_icon_white);
            reportOtherImg.setImageResource(R.drawable.circle_icon_white);
            reportTitle = "违法违规内容";
        } else if (view == reportHostilityRel) {
            reportHostilityImg.setImageResource(R.mipmap.btn_check_selected);
            reportIrregularityImg.setImageResource(R.drawable.circle_icon_white);
            reportTrashInfoImg.setImageResource(R.drawable.circle_icon_white);
            reportAdContentImg.setImageResource(R.drawable.circle_icon_white);
            reportOtherImg.setImageResource(R.drawable.circle_icon_white);
            reportTitle = "不友善内容";
        } else if (view == reportOtherRel) {
            reportAdContentImg.setImageResource(R.drawable.circle_icon_white);
            reportHostilityImg.setImageResource(R.drawable.circle_icon_white);
            reportTrashInfoImg.setImageResource(R.drawable.circle_icon_white);
            reportIrregularityImg.setImageResource(R.drawable.circle_icon_white);
            reportOtherImg.setImageResource(R.mipmap.btn_check_selected);
            reportTitle = "其他";
        } else if (view == reportSubmitBtn) {
            report();
        }
    }

    /***
     *举报
     */
    private void report() {

        String reportContentStr = reportContentEdit.getText().toString();
        if (TextUtils.isEmpty(reportContentStr)) {
            Toasty.warning(this, "举报内容不能为空").show();
            return;
        }
        ReportParam reportParam = new ReportParam();
        reportParam.setContent(reportContentStr);
        reportParam.setTitle(reportTitle);
        reportParam.setReportType(getType());
        reportParam.setDynamicId(getDynamicId());
        reportParam.setUserId(getUserId());
        String userId = UserDataManager.getInstance().getUserId();
        ABHttp.getIns().postJSON(UrlConfig.report + "?userId=" + userId, JsonUtils.toJson(reportParam), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(ReportActivity.this, msg).show();
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        ReportActivity.this.finish();
                    }
                }
            }
        });

    }
}
