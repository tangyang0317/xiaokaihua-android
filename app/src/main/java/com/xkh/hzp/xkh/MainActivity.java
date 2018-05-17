package com.xkh.hzp.xkh;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.xkh.hzp.xkh.activity.PublishPictureTextActvity;
import com.xkh.hzp.xkh.fragment.FragmentFactory;
import com.xkh.hzp.xkh.http.RetrofitHttp;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * Created by tangyang on 18/4/21.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup indexRadioGroup;
    private RelativeLayout contentBgLayout;
    private FloatingActionButton floatingActionButton;
    private SubActionButton.Builder rLSubBuilder;
    private FloatingActionMenu rightLowerMenu;
    private ImageView addIconImg, videoImg, imgTextImg;
    private ArrayList<ValueAnimator> animList = new ArrayList<>();


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_fb435b).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        hideToolbar();
        indexRadioGroup = findViewById(R.id.indexRadioGroup);
        contentBgLayout = findViewById(R.id.contentBgLayout);
        if (contentBgLayout.getForeground() == null) {
            contentBgLayout.setForeground(new ColorDrawable(Color.parseColor("#ffffff")));
        }

        contentBgLayout.getForeground().setAlpha(0);
        initfFloatButton();
        initFragment(0);
//        RetrofitHttp.getInstence().API().getStringBanner("index").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                Log.d("xkh", response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
    }


    /**
     * 悬浮菜单
     */
    private void initfFloatButton() {

        addIconImg = new ImageView(this);
        FloatingActionButton.LayoutParams layoutParams = new FloatingActionButton.LayoutParams(DimentUtils.dip2px(this, 60), DimentUtils.dip2px(this, 60));
        layoutParams.bottomMargin = DimentUtils.dip2px(this, 48);
        layoutParams.rightMargin = DimentUtils.dip2px(this, 20);

        addIconImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_vedio));
        addIconImg.setScaleType(ImageView.ScaleType.FIT_XY);
        floatingActionButton = new FloatingActionButton.Builder(this).setContentView(addIconImg)
                .setLayoutParams(layoutParams)
                .build();

        FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(DimentUtils.dip2px(this, 50), DimentUtils.dip2px(this, 50));

        rLSubBuilder = new SubActionButton.Builder(this)
                .setLayoutParams(layoutParams1);
        videoImg = new ImageView(this);
        imgTextImg = new ImageView(this);
        videoImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_vedio));
        imgTextImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_img_text));

        rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(videoImg).build())
                .addSubActionView(rLSubBuilder.setContentView(imgTextImg).build())
                .setRadius(150)
                .attachTo(floatingActionButton).build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // 增加按钮中的+号图标顺时针旋转45度
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                executeAlphaAnimation(0, 180, 200);
                addIconImg.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(addIconImg, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // 增加按钮中的+号图标逆时针旋转45度
                // Rotate the icon of rightLowerButton 45 degrees
                // counter-clockwise
                executeAlphaAnimation(180, 0, 200);
                addIconImg.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(addIconImg, pvhR);
                animation.start();
            }
        });

    }


    private void executeAlphaAnimation(final int from, final int to, final int duration) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newAlpha = (int) animation.getAnimatedValue();
                contentBgLayout.getForeground().setAlpha(newAlpha);
                //anim finished
                if (newAlpha == to) {
                    valueAnimator.cancel();
                    animList.clear();
                }
            }
        });
        valueAnimator.start();
        animList.add(valueAnimator);
    }


    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void setListenner() {
        indexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.dynamicRB:
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_fb435b).init();
                        floatingActionButton.setVisibility(View.VISIBLE);
                        initFragment(0);
                        break;
                    case R.id.talentRB:
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_fb435b).init();
                        floatingActionButton.setVisibility(View.GONE);
                        if (rightLowerMenu.isOpen()) rightLowerMenu.close(true);
                        initFragment(1);
                        break;
                    case R.id.mineRB:
                        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
                        floatingActionButton.setVisibility(View.GONE);
                        if (rightLowerMenu.isOpen()) rightLowerMenu.close(true);
                        initFragment(2);
                        break;
                }
            }
        });

        videoImg.setOnClickListener(this);
        imgTextImg.setOnClickListener(this);
    }


    public void initFragment(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = FragmentFactory.createFragment(i);
        transaction.replace(R.id.mainContainerFrameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == videoImg) {

        } else if (view == imgTextImg) {
            rightLowerMenu.close(true);
            PublishPictureTextActvity.lunchActivity(this, null, PublishPictureTextActvity.class);
        }
    }
}
