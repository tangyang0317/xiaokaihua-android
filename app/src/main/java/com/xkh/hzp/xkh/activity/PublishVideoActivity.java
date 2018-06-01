package com.xkh.hzp.xkh.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.utils.GetPathFromUri;
import com.xkh.hzp.xkh.view.Views;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.AbDevice;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.UILoadingView;

/**
 * 发布视频页面
 *
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName PublishVideoActivity
 * @Author tangyang
 * @DATE 2018/5/23
 **/
public class PublishVideoActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvReplaceFace, tvReplaceVedio;
    private EditText dynamicContentEdit;
    private NormalGSYVideoPlayer videoPlayerView;

    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption = null;
    private String videoLocalPath = "";
    private String faceImgUrl = null;
    private String dynamicContent = null;

    private UILoadingView uiLoadingView = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_video;
    }

    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);
        dynamicContent = dynamicContentEdit.getText().toString();
        if (TextUtils.isEmpty(videoLocalPath)) {
            Toasty.warning(PublishVideoActivity.this, "请选择需要上传的视频").show();
            return;
        }

        if (TextUtils.isEmpty(faceImgUrl)) {
            Toasty.warning(PublishVideoActivity.this, "请选择需要上传的视频").show();
            return;
        }

        if (TextUtils.isEmpty(dynamicContent)) {
            Toasty.warning(PublishVideoActivity.this, "请输入动态内容").show();
            return;
        }

        uiLoadingView = new UILoadingView(this, false, "正在上传视频");

        uploadVideo();
    }


    /***
     * 上传完成结果回掉
     */
    public UpCompletionHandler upCompletionHandler = new UpCompletionHandler() {
        @Override
        public void complete(String key, ResponseInfo info, JSONObject response) {
            if (info.isOK()) {
                uiLoadingView.setNoticeTitle("视频上传成功");
                uiLoadingView.setNoticeTitle("动态内容发布中");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uiLoadingView.dismiss();
                    }
                }, 2000);
            }
            Logger.d("complete key----->" + key);
            Logger.d("complete ResponseInfo----->" + info.toString());
            Logger.d("complete response----->" + response);
        }
    };


    /***
     * 上传进度回掉
     */
    public UpProgressHandler upProgressHandler = new UpProgressHandler() {
        @Override
        public void progress(String key, double percent) {
            int progress = (int) (percent * 100);
            uiLoadingView.setNoticeTitle("请耐心等待:" + String.valueOf(progress + "%"));
            Logger.d("progress response----->" + percent);
        }
    };


    /***
     * 上传视频
     */
    private void uploadVideo() {

        uiLoadingView.show();
        com.qiniu.android.storage.Configuration config = new com.qiniu.android.storage.Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .build();

        UploadManager uploadManager = new UploadManager(config);

        UploadOptions uploadOptions = new UploadOptions(null, null, false, upProgressHandler, null);

//        uploadManager.put(new File(videoLocalPath), String.valueOf(System.currentTimeMillis() * 100 * 1000), Config.QINIU_VIDEO_TOKEN, upCompletionHandler, uploadOptions);

    }

    @Override
    public void initView() {
        setToolbarTitleTv("发视频");
        setRightTitleTxt("发布");
        videoPlayerView = findViewById(R.id.videoPlayerView);
        tvReplaceFace = findViewById(R.id.tvReplaceFace);
        tvReplaceVedio = findViewById(R.id.tvReplaceVedio);
        dynamicContentEdit = findViewById(R.id.dynamicVedioContentEdit);
        pickLocalFile();
        videoPlayerView.post(new Runnable() {
            @Override
            public void run() {
                Views.setHeightByRatio(videoPlayerView, AbDevice.SCREEN_WIDTH_PX, 9.0 / 16);
            }
        });

        initVideo();
    }

    /***
     * 播放器初始化
     */
    private void initVideo() {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.example);
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayerView);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        Glide.with(PublishVideoActivity.this).load("").placeholder(R.mipmap.img_video_def).into(imageView);
        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(videoLocalPath)
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .build(videoPlayerView);

        videoPlayerView.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayerView.startWindowFullscreen(PublishVideoActivity.this, true, true);
            }
        });
        getCurPlay().getTitleTextView().setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayerView.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        videoPlayerView.getTitleTextView().setVisibility(View.GONE);
        videoPlayerView.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (videoPlayerView.getFullWindowPlayer() != null) {
            return videoPlayerView.getFullWindowPlayer();
        }
        return videoPlayerView;
    }


    @Override
    public void setListenner() {
        tvReplaceFace.setOnClickListener(this);
        tvReplaceVedio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvReplaceFace) {
            setVideoFace();
        } else if (view == tvReplaceVedio) {
            pickLocalFile();
        }
    }


    /**
     * 设置视频的封面
     **/
    public void setVideoFace() {
        RichEditComponentSample richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(new TuMutipleHandle() {
            @Override
            public void onMultipleTuSuccess(List<String> images) {
                if (images != null && images.size() > 0) {
                    /*****拿到返回的图片路径******/
                    faceImgUrl = images.get(0);
                    if (gsyVideoOption != null) {
                        ImageView imageView = new ImageView(PublishVideoActivity.this);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(PublishVideoActivity.this).load(new File(faceImgUrl)).placeholder(R.mipmap.img_video_def).into(imageView);
                        gsyVideoOption.setThumbImageView(imageView);
                        gsyVideoOption.build(videoPlayerView);
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                Logger.d(msg);
            }
        });
        richEditComponentSample.showSample(PublishVideoActivity.this, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if (requestCode == Config.CHOOSE_VIDEO_CODE) {
            if (data == null || data.getData() == null) {
                return;
            }
            /***解析视频文件地址***/
            videoLocalPath = GetPathFromUri.getPath(this, data.getData());
            if (videoLocalPath != null && !"".equals(videoLocalPath)) {
                if (gsyVideoOption != null) {
                    /*****设置本地文件播放******/
                    gsyVideoOption.setUrl("file://" + videoLocalPath);
                    gsyVideoOption.build(videoPlayerView);
                    getCurPlay().startPlayLogic();
                }
            }
        }
    }

    /*****
     * 选择本地视频的选择器
     */
    public void pickLocalFile() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("video/*");
        }
        startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), Config.CHOOSE_VIDEO_CODE);
    }


}
