package xkh.hzp.xkh.com.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
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

    private Toolbar baseToolBar;
    private TextView toolBarTitleTxt, toolBarRightTxt;
    private ImageView toolBarRightImg;
    private LinearLayout baseContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getBaseContentView());
        initBaseControllerView();
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

    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 初始化控制器View
     */
    private void initBaseControllerView() {
        //设置沉浸式菜单栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true, 0.2f).statusBarColor(R.color.toolBarBg).init();
        baseToolBar = findViewById(R.id.baseToolBar);
        toolBarTitleTxt = findViewById(R.id.toolBarTitleTxt);
        toolBarRightTxt = findViewById(R.id.toolBarRightTxt);
        toolBarRightImg = findViewById(R.id.toolBarRightImg);
        baseContentLayout = findViewById(R.id.baseContentLayout);
        setTitleNavigationIcon(R.drawable.icon_back);
        setToolBarBg(R.color.toolBarBg);
        baseContentLayout.addView(LinearLayout.inflate(this, getLayoutId(), null));
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


    protected int getBaseContentView() {
        return R.layout.acitivyt_base;
    }


    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setToolbarTitleTv(String title) {
        toolBarTitleTxt.setText(title);
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
     * 设置标题栏背景颜色
     *
     * @param color
     */
    protected void setToolBarBg(int color) {
        baseToolBar.setBackgroundColor(getResources().getColor(color));
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
        ImmersionBar.with(this).destroy();
    }

}
