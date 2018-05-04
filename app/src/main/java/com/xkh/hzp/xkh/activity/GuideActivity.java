package com.xkh.hzp.xkh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xkh.hzp.xkh.MainActivity;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName GuideActivity
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private List<View> viewList;//图片资源的集合
    private ViewGroup vg;//放置圆点
    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;
    //最后一页的按钮
    private ImageView ib_start;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp = null;
        viewList = null;
        ivPointArray = null;
        iv_point = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ib_start = findViewById(R.id.guide_ib_start);
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedprefrenceHelper.getIns(GuideActivity.this).put("first_guide", false);
                MainActivity.lunchActivity(GuideActivity.this, null, MainActivity.class);
                GuideActivity.this.finish();
            }
        });
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.setMargins(5, 0, 5, 0);
            iv_point.setLayoutParams(layoutParams);
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.point_select);
            } else {
                iv_point.setBackgroundResource(R.drawable.point_unselect);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }


    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        View view1 = LayoutInflater.from(this).inflate(R.layout.view_guide1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.view_guide2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.view_guide3, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuideAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        for (int i = 0; i < viewList.size(); i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.point_select);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.point_unselect);
            }
        }
        //判断是否是最后一页，若是则显示按钮
        if (position == viewList.size() - 1) {
            vg.setVisibility(View.INVISIBLE);
            ib_start.setVisibility(View.VISIBLE);
        } else {
            ib_start.setVisibility(View.INVISIBLE);
            vg.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
