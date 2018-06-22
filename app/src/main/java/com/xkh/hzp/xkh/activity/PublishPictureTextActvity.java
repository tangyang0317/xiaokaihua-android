package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.request.PublishDynamicParam;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.upload.OnUploadListener;
import com.xkh.hzp.xkh.upload.UploadImageManager;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.adapter.GridViewAddImgesAdpter;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.UILoadingView;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName PublishPictureTextActvity
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class PublishPictureTextActvity extends BaseActivity implements TuMutipleHandle {

    private EditText pictureTxtEdit;
    private GridView imgGridView;
    private GridViewAddImgesAdpter addImgesAdpter;
    private RichEditComponentSample richEditComponentSample;
    private List<String> localImgFilePath = new ArrayList<>();
    private List<String> qiNiuimg = new ArrayList<>();
    private UILoadingView loadingView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_picture_text;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_fc4d5e), 0);
    }

    @Override
    protected void setToolbarBgColor() {
        super.setToolbarBgColor();
        baseToolBar.setBackground(getResources().getDrawable(R.drawable.shape_bar_red_bg));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("发动态");
        setRightTitleTxt("发布");
        hideToolbarBottomLine();
        setTitleNavigationIcon(R.drawable.icon_back);
        setToolBarTitleTextColor(getResources().getColor(R.color.color_ffffff));
        richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(this);
        pictureTxtEdit = findViewById(R.id.pictureTxtEdit);
        imgGridView = findViewById(R.id.imgGridView);
        addImgesAdpter = new GridViewAddImgesAdpter(null, this, 5);
        addImgesAdpter.setMaxImages(9);
        imgGridView.setAdapter(addImgesAdpter);

    }


    /***
     * 发布动态
     */
    public void publishDynamic(List<String> imgList, String dynamicTxt, String dynamicType) {
        PublishDynamicParam publishDynamicParam = new PublishDynamicParam();
        publishDynamicParam.setAnnexUrl(imgList);
        publishDynamicParam.setDynamicType(dynamicType);
        publishDynamicParam.setWordDescription(dynamicTxt);
        String userId = String.valueOf(UserDataManager.getInstance().getUserId());
        ABHttp.getIns().postJSON(UrlConfig.publishDynamic + "?userId=" + userId, JsonUtils.toJson(publishDynamicParam), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Integer.TYPE);
            }

            @Override
            public boolean onFailure(String code, String msg) {
                Toasty.info(PublishPictureTextActvity.this, "动态发布失败").show();
                loadingView.dismiss();
                return true;
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadingView.dismiss();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                loadingView.dismiss();
                if (success) {
                    Toasty.info(PublishPictureTextActvity.this, "发布成功").show();
                    hideKeyBoard();
                    PublishPictureTextActvity.this.finish();
                } else {
                    Toasty.info(PublishPictureTextActvity.this, "发布失败").show();
                }
            }
        });

    }


    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);

        final String pictureTxtStr = pictureTxtEdit.getText().toString();
        if (addImgesAdpter.getDatas().size() < 1) {
            Toasty.warning(PublishPictureTextActvity.this, "请至少选择一张图片").show();
            return;
        }
        if (TextUtils.isEmpty(pictureTxtStr) || pictureTxtStr.length() > 150) {
            Toasty.warning(PublishPictureTextActvity.this, "请输入最多150个字的动态内容").show();
            return;
        }

        loadingView = new UILoadingView(this, false, "正在上传图片");
        loadingView.show();
        UploadImageManager.getInstances().doUpload(this, addImgesAdpter.getDatas(), new OnUploadListener() {
            @Override
            public void onAllSuccess(List<HashMap<String, Object>> allImages) {
                if (allImages != null && allImages.size() > 0) {
                    for (int i = 0; i < allImages.size(); i++) {
                        qiNiuimg.add((String) allImages.get(i).get("url"));
                    }
                    publishDynamic(qiNiuimg, pictureTxtStr, "image");
                }
            }

            @Override
            public void onAllFailed(String message) {
                loadingView.dismiss();
                Toasty.error(PublishPictureTextActvity.this, message).show();
            }

            @Override
            public void onThreadFinish(int position) {

            }
        });
    }

    @Override
    public void setListenner() {
        imgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == adapterView.getChildCount() - 1) {
                    //限数量的多选(比喻最多9张)
                    if (localImgFilePath.size() >= 9) {
                        Toasty.info(PublishPictureTextActvity.this, "最多只能选9张图片", Toast.LENGTH_SHORT).show();
                    } else {
                        richEditComponentSample.showSample(PublishPictureTextActvity.this, 9 - localImgFilePath.size());
                    }
                }
            }
        });
    }

    @Override
    public void onMultipleTuSuccess(List<String> filePath) {
        localImgFilePath.addAll(filePath);
        addImgesAdpter.notifyDataSetChanged(localImgFilePath);
    }

    @Override
    public void onFail(String msg) {
        Toasty.error(PublishPictureTextActvity.this, "涂图处理图片失败").show();
    }
}
