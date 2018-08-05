package com.xkh.hzp.xkh.view;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;

import java.util.List;

import xkh.hzp.xkh.com.base.view.NineGridLayout;
import xkh.hzp.xkh.com.base.view.RatioImageView;

/**
 * @packageName com.xkh.hzp.xkh.view
 * @FileName XkhNineGridLayout
 * @Author tangyang
 * @DATE 2018/7/10
 **/
public class XkhNineGridLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;
    private Context context;

    public XkhNineGridLayout(Context context) {
        super(context);
        this.context = context;
    }

    public XkhNineGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        Glide.with(context).load(url).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                int w = resource.getIntrinsicWidth();
                int h = resource.getIntrinsicHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                Logger.d("-----width+" + newW + "-----");
                Logger.d("-----height+" + newH + "-----");
                setOneImageLayoutParams(imageView, newW, newH);
                return false;
            }
        }).into(imageView);

        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        Glide.with(context).load(url).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(imageView);
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {

    }
}
