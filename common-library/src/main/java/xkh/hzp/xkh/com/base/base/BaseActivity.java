package xkh.hzp.xkh.com.base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xkh.hzp.xkh.com.R;
import xkh.hzp.xkh.com.base.utils.ToastUtils;

/**
 * Author 唐洋
 * Date 2018/4/24
 * 描述 BaseActivity，提供简单调用方法，封装toolbar，沉浸式菜单栏
 **/
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar baseToolBar;
    protected View toolBarBottomLine;
    private TextView toolBarTitleTxt, toolBarRightTxt;
    private ImageView toolBarRightImg;
    private FrameLayout baseContentLayout;
    protected LinearLayout baseContainerLayout;

    public static void lunchActivity(Activity activity, Bundle bundle, Class tClass) {
        Intent intent = new Intent(activity, tClass);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivyt_base);
        setStatusBar();
        initBaseControllerView();
        AppManager.getAppManager().addActivity(this);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_c8000000), 0);
    }

    protected void setToolBarTitleTextColor(int color) {
        toolBarTitleTxt.setTextColor(color);
    }

    protected void setToolBarRightTextColor(int color) {
        toolBarRightTxt.setTextColor(color);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //做数据统计
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void setBaseContainerBg() {
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_f3f5fa));
    }

    /**
     * 初始化控制器View
     */
    private void initBaseControllerView() {
        baseToolBar = findViewById(R.id.baseToolBar);
        toolBarTitleTxt = findViewById(R.id.toolBarTitleTxt);
        toolBarRightTxt = findViewById(R.id.toolBarRightTxt);
        toolBarRightImg = findViewById(R.id.toolBarRightImg);
        baseContentLayout = findViewById(R.id.baseContentLayout);
        baseContainerLayout = findViewById(R.id.baseContainerLayout);
        toolBarBottomLine = findViewById(R.id.toolBarBottomLine);
        setTitleNavigationIcon(R.drawable.icon_back_black);
        setToolbarBgColor();
        baseContentLayout.addView(LinearLayout.inflate(this, getLayoutId(), null));
        setBaseContainerBg();
        initView();
        setListenner();
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackOnClickNavigationAction(view);
            }
        });
        toolBarRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackOnclickRightMenu(view);
            }
        });

        toolBarRightTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackOnclickRightMenu(view);
            }
        });
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setToolbarTitleTv(String title) {
        toolBarTitleTxt.setText(title);
    }

    @SuppressLint("ResourceType")
    protected void setToolbarBgColor() {
        baseToolBar.setBackground(getResources().getDrawable(R.drawable.shape_bar_white_bg));
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    public void setRightTitleTxt(String text) {
        toolBarRightImg.setVisibility(View.GONE);
        toolBarRightTxt.setVisibility(View.VISIBLE);
        toolBarRightTxt.setText(text);
    }


    /**
     * 设置右边图标
     *
     * @param rightImageRes
     */
    public void setRightImage(int rightImageRes) {
        toolBarRightTxt.setVisibility(View.GONE);
        toolBarRightImg.setVisibility(View.VISIBLE);
        toolBarRightImg.setImageResource(rightImageRes);
    }


    /**
     * 设置左边标题图标
     *
     * @param iconRes
     */
    public void setTitleNavigationIcon(int iconRes) {
        baseToolBar.setNavigationIcon(iconRes);
    }

    /**
     * 隐藏标题栏
     */
    protected void hideToolbar() {
        if (baseToolBar.getVisibility() == View.VISIBLE)
            baseToolBar.setVisibility(View.GONE);

        if (toolBarBottomLine.getVisibility() == View.VISIBLE)
            toolBarBottomLine.setVisibility(View.GONE);
    }

    /**
     * 隐藏标题栏底部的分割线
     */
    protected void hideToolbarBottomLine() {
        if (toolBarBottomLine.getVisibility() == View.VISIBLE)
            toolBarBottomLine.setVisibility(View.GONE);
    }

    /**
     * 隐藏右边的操作按钮
     */
    protected void hideRightMenu() {
        toolBarRightTxt.setVisibility(View.GONE);
        toolBarRightImg.setVisibility(View.GONE);
    }

    /**
     * 不显示 NavigationButton
     */
    public void hideTitleNavigationButton() {
        baseToolBar.setNavigationIcon(null);
    }

    /**
     * Navigation Button点击回调，默认回退销毁页面，其他操作子类可重写
     *
     * @param view
     */
    protected void callbackOnClickNavigationAction(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyBoard();
    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    protected void callbackOnclickRightMenu(View view) {

    }


    public boolean isFinishingOrDestroyed() {
        return isFinishing() || getSupportFragmentManager().isDestroyed();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void setListenner();

    public void showToast(String toastMsg) {
        ToastUtils.showToast(this, toastMsg);
    }

    public void showLongToast(String toastMsg) {
        ToastUtils.showLongToast(this, toastMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
