package com.xkh.hzp.xkh.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
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
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.entity.CommentBean;
import com.xkh.hzp.xkh.entity.CommentDetailBean;
import com.xkh.hzp.xkh.entity.ReplyDetailBean;
import com.xkh.hzp.xkh.view.CommentExpandableListView;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * 视频动态详情Activity
 *
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName VideoDynamicDetailsActivity
 * @Author tangyang
 * @DATE 2018/5/14
 **/
public class VideoDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    private NormalGSYVideoPlayer videoPlayerView;
    private NestedScrollView contentScrollView;
    private ImageView userHeadImg, leftBackImg;
    private TextView userNickNameTxt, videoDynamicTitleTxt, userIsAttentionTxt, dynamicContentTxt, dynamicDateTxt;
    private CommentExpandableListView commentExpandableListView;
    private LinearLayout vgBottomInfo, navigationLayout;
    private RelativeLayout detailsShareLayout, detailsCommentLayout, detailsPraiseLayout;
    private ImageView detailsPraiseImg, detailsCommentImg, detailsSharedImg;
    private TextView detailsPraiseTxt, detailsCommentTxt;

    private CommentExpandAdapter commentExpandAdapter;
    private List<CommentDetailBean> commentsList;
    private CommentBean commentBean;
    private BottomSheetDialog bottomSheetDialog;

    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private String url = "http://7xse1z.com1.z0.glb.clouddn.com/1491813192";

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_dynamic_details;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.topView).init();
    }

    @Override
    public void initView() {
        hideToolbar();
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
        leftBackImg = findViewById(R.id.leftBackImg);
        videoDynamicTitleTxt = findViewById(R.id.videoDynamicTitleTxt);
        contentScrollView = findViewById(R.id.contentScrollView);
        videoPlayerView = findViewById(R.id.videoPlayerView);
        userHeadImg = findViewById(R.id.userHeadImg);
        userNickNameTxt = findViewById(R.id.userNickNameTxt);
        userIsAttentionTxt = findViewById(R.id.userIsAttentionTxt);
        dynamicContentTxt = findViewById(R.id.dynamicContentTxt);
        dynamicDateTxt = findViewById(R.id.dynamicDateTxt);
        commentExpandableListView = findViewById(R.id.commentExpandableListView);
        navigationLayout = findViewById(R.id.navigationLayout);
        vgBottomInfo = findViewById(R.id.vgBottomInfo);
        detailsShareLayout = findViewById(R.id.detailsShareLayout);
        detailsCommentLayout = findViewById(R.id.detailsCommentLayout);
        detailsPraiseLayout = findViewById(R.id.detailsPraiseLayout);
        detailsPraiseImg = findViewById(R.id.detailsPraiseImg);
        detailsCommentImg = findViewById(R.id.detailsCommentImg);
        detailsSharedImg = findViewById(R.id.detailsSharedImg);
        detailsPraiseTxt = findViewById(R.id.detailsPraiseTxt);
        detailsCommentTxt = findViewById(R.id.detailsCommentTxt);
        initVideo();
        navigationLayout.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                , ContextCompat.getColor(this, R.color.color_ff5555), 0));
        commentsList = generateTestData();
        commentExpandAdapter = new CommentExpandAdapter(this, commentsList);
        commentExpandableListView.setAdapter(commentExpandAdapter);
        commentExpandableListView.setGroupIndicator(null);
        //默认展开所有回复
        for (int i = 0; i < commentsList.size(); i++) {
            commentExpandableListView.expandGroup(i);
        }
        commentExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
//                showReplyDialog(groupPosition);
                return true;
            }
        });

        commentExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                return false;
            }
        });

        commentExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        contentScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.d("scrollY" + scrollY + "------>oldScrollY" + oldScrollY);
                int videoPlayerViewHeight = videoPlayerView.getHeight() - navigationLayout.getHeight() - ImmersionBar.getStatusBarHeight(VideoDynamicDetailsActivity.this);
                if (scrollY <= videoPlayerViewHeight) {
                    videoDynamicTitleTxt.setVisibility(View.GONE);
                    float alpha = (float) scrollY / videoPlayerViewHeight;
                    navigationLayout.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(VideoDynamicDetailsActivity.this, R.color.color_ff5555), alpha));
                } else {
                    videoDynamicTitleTxt.setVisibility(View.VISIBLE);
                    navigationLayout.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(VideoDynamicDetailsActivity.this, R.color.color_ff5555), 1));
                }
            }
        });

    }

    /**
     * 显示编辑输入框Dialog
     */
    private void showEditDialog() {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        bottomSheetDialog.setContentView(contentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) contentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        behavior.setPeekHeight(contentView.getMeasuredHeight());
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                InputMethodManager inputMgr = (InputMethodManager) VideoDynamicDetailsActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        });

    }


    private List<CommentDetailBean> generateTestData() {
        List<CommentDetailBean> commentDetailBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentDetailBean commentDetailBean = new CommentDetailBean("爱吃糖的羊", "这条笔记不错" + i, "一分钟前");
            List<ReplyDetailBean> replyDetailBeanList = new ArrayList<ReplyDetailBean>();
            for (int j = 0; j < 5; j++) {
                ReplyDetailBean replyDetailBean = new ReplyDetailBean("勒布朗", "下场比赛一定要赢");
                replyDetailBeanList.add(replyDetailBean);
            }
            commentDetailBean.setReplyList(replyDetailBeanList);
            commentDetailBeanList.add(commentDetailBean);
        }
        return commentDetailBeanList;
    }


    /***
     * 初始化视频
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

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
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
                videoPlayerView.startWindowFullscreen(VideoDynamicDetailsActivity.this, true, true);
            }
        });


    }

    @Override
    public void setListenner() {
        userHeadImg.setOnClickListener(this);
        userIsAttentionTxt.setOnClickListener(this);
        detailsShareLayout.setOnClickListener(this);
        detailsCommentLayout.setOnClickListener(this);
        detailsPraiseLayout.setOnClickListener(this);

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
        //GSYPreViewManager.instance().releaseMediaPlayer();
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
    public void onClick(View view) {
        if (view == userIsAttentionTxt) {

        } else if (view == detailsShareLayout) {

        } else if (view == userHeadImg) {

        } else if (view == detailsCommentLayout) {
            showEditDialog();
        } else if (view == detailsPraiseLayout) {

        }
    }
}
