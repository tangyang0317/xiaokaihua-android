package com.xkh.hzp.xkh.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.view
 * @FileName MultipleImageView
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class MultipleImageView extends View {

    private Context context;
    private List<String> imgList = new ArrayList<>();
    private int screenWidth = 0;

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public MultipleImageView(Context context) {
        super(context);
        this.context = context;
        screenWidth = DimentUtils.getScreenWidth(context);
    }

    public MultipleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        screenWidth = DimentUtils.getScreenWidth(context);
    }

    public MultipleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        screenWidth = DimentUtils.getScreenWidth(context);
        initView();
    }

    /***
     *
     */
    private void initView() {
        if (imgList != null && imgList.size() > 0) {
            switch (imgList.size()) {
                case 1:
                    createOneImg();
                    break;
                case 2:
                    createTwoImg();
                    break;
                case 3:
                    createThreeImg();
                    break;
                case 4:
                    createFourImg();
                    break;
            }
        }
    }

    private void createFourImg() {
    }


    private void createThreeImg() {
    }

    private void createTwoImg() {
    }

    private void createOneImg() {
        ImageView imageView = new ImageView(context);


    }
}
