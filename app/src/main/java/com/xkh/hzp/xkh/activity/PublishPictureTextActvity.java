package com.xkh.hzp.xkh.activity;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.upload.OnUploadListener;
import com.xkh.hzp.xkh.upload.UploadImageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.adapter.GridViewAddImgesAdpter;
import xkh.hzp.xkh.com.base.base.BaseActivity;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_picture_text;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("发动态");
        setRightTitleTxt("发布");
        richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(this);
        pictureTxtEdit = findViewById(R.id.pictureTxtEdit);
        imgGridView = findViewById(R.id.imgGridView);
        addImgesAdpter = new GridViewAddImgesAdpter(null, this, 5);
        addImgesAdpter.setMaxImages(9);
        imgGridView.setAdapter(addImgesAdpter);

    }


    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);
        final UILoadingView loadingView = new UILoadingView(this, false, "正在上传视频");

        loadingView.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.dismiss();
            }
        }, 5000);

//        UploadImageManager.getInstances().doUpload(this, addImgesAdpter.getDatas(), new OnUploadListener() {
//            @Override
//            public void onAllSuccess(List<HashMap<String, Object>> allImages) {
//
//                for (HashMap<String, Object> allImage : allImages) {
//                    Logger.d(allImage.get(""));
//                }
//            }
//
//            @Override
//            public void onAllFailed(String message) {
//
//            }
//
//            @Override
//            public void onThreadFinish(int position) {
//
//            }
//        });

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
