package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.request.JoinUsParam;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.upload.OnUploadListener;
import com.xkh.hzp.xkh.upload.UploadImageManager;
import com.xkh.hzp.xkh.utils.RegExpValidatorUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.adapter.GridViewAddImgesAdpter;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.view.UILoadingView;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName JoinTalentActivity
 * @Author tangyang
 * @DATE 2018/5/10
 **/
public class JoinTalentActivity extends BaseActivity implements View.OnClickListener, TuMutipleHandle {

    private Button joinUsSubmitBtn;
    private EditText talentLinkManNameEdit, talentLinkManPhoneEdit, talentWeiBoEdit, talentHeightEdit, talentWidghtEdit;
    private String talentLinkManNameStr, talentLinkManPhoneStr, talentWeiBoStr, talentHeightStr, talentWidghtStr;
    private GridView joinTalentGridView;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private List<String> localImgFilePath = new ArrayList<>();
    private List<String> qiNiuimg = new ArrayList<>();
    private RichEditComponentSample richEditComponentSample;
    private UILoadingView uiLoadingView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_join_talent;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setToolbarTitleTv("加入达人");
        joinTalentGridView = findViewById(R.id.joinTalentGridView);
        talentLinkManNameEdit = findViewById(R.id.talentLinkManNameEdit);
        talentLinkManPhoneEdit = findViewById(R.id.talentLinkManPhoneEdit);
        talentWeiBoEdit = findViewById(R.id.talentWeiBoEdit);
        talentHeightEdit = findViewById(R.id.talentHeightEdit);
        talentWidghtEdit = findViewById(R.id.talentWidghtEdit);
        joinUsSubmitBtn = findViewById(R.id.joinUsSubmitBtn);
        richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(this);
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(null, this, 3);
        gridViewAddImgesAdpter.setMaxImages(3);
        joinTalentGridView.setAdapter(gridViewAddImgesAdpter);

    }

    @Override
    public void onMultipleTuSuccess(List<String> filePath) {
        localImgFilePath.addAll(filePath);
        gridViewAddImgesAdpter.notifyDataSetChanged(localImgFilePath);
    }

    @Override
    public void onFail(String msg) {
        Toasty.error(JoinTalentActivity.this, "涂图处理图片失败").show();
    }

    @Override
    public void setListenner() {
        joinUsSubmitBtn.setOnClickListener(this);
        joinTalentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == adapterView.getChildCount() - 1) {
                    //限数量的多选(比喻最多9张)
                    if (localImgFilePath.size() > 3) {
                        Toasty.info(JoinTalentActivity.this, "最多只能选3张图片", Toast.LENGTH_SHORT).show();
                    } else {
                        richEditComponentSample.showSample(JoinTalentActivity.this, 3 - localImgFilePath.size());
                    }
                }
            }
        });
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

            if (!RegExpValidatorUtils.IsHandset(talentLinkManPhoneStr)) {
                Toasty.warning(JoinTalentActivity.this, "手机号码不合法").show();
                return;
            }

            if (TextUtils.isEmpty(talentWeiBoStr)) {
                Toasty.warning(JoinTalentActivity.this, "请填写微博昵称").show();
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

            if (gridViewAddImgesAdpter.getDatas() == null) {
                Toasty.warning(JoinTalentActivity.this, "照片不能为空").show();
                return;
            }

            if (gridViewAddImgesAdpter.getDatas().size() != 3) {
                Toasty.warning(JoinTalentActivity.this, "请上传3张照片").show();
                return;
            }
            doUploadImg();
        }
    }

    /*****
     * 1.上传图片
     * 2.请求业务服务期，提交数据
     */
    private void doUploadImg() {
        uiLoadingView = new UILoadingView(this, false, "正在上传图片");
        uiLoadingView.show();
        UploadImageManager.getInstances().doUpload(this, gridViewAddImgesAdpter.getDatas(), new OnUploadListener() {
            @Override
            public void onAllSuccess(List<HashMap<String, Object>> allImages) {
                if (allImages != null && allImages.size() > 0) {
                    for (int i = 0; i < allImages.size(); i++) {
                        qiNiuimg.add((String) allImages.get(i).get("url"));
                    }
                    doJoinUs(qiNiuimg);
                }
            }

            @Override
            public void onAllFailed(String message) {
                uiLoadingView.dismiss();
                Toasty.error(JoinTalentActivity.this, message).show();
            }

            @Override
            public void onThreadFinish(int position) {

            }
        });
    }

    /****
     * 提交数据，请求业务服务器
     * @param imgList
     */
    private void doJoinUs(List<String> imgList) {
        JoinUsParam joinUsParam = new JoinUsParam();
        joinUsParam.setHigh(Integer.parseInt(talentHeightStr));
        joinUsParam.setWeight(Integer.parseInt(talentWidghtStr));
        joinUsParam.setMicroblogName(talentWeiBoStr);
        joinUsParam.setPhone(talentLinkManPhoneStr);
        joinUsParam.setContactName(talentLinkManNameStr);
        joinUsParam.setApplyImageList(imgList);
        joinUsParam.setUserId(UserDataManager.getInstance().getUserId());
        ABHttp.getIns().postJSON(UrlConfig.talentJoinUs, JsonUtils.toJson(joinUsParam), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                qiNiuimg.clear();
                uiLoadingView.dismiss();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    JoinTalentActivity.this.finish();
                    Toasty.info(JoinTalentActivity.this, "提交成功").show();
                } else {
                    Toasty.info(JoinTalentActivity.this, msg).show();
                }
            }
        });


    }
}
