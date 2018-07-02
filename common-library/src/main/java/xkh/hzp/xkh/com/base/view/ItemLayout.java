package xkh.hzp.xkh.com.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xkh.hzp.xkh.com.R;

/**
 * @packageName xkh.hzp.xkh.com.base.view
 * @FileName ItemLayout
 * @Author tangyang
 * @DATE 2018/5/3
 **/

public class ItemLayout extends LinearLayout {

    private Context context;
    private ImageView leftIconImg, rightIconImg;
    private TextView leftTitleTxt, rightTitleTxt;

    private int leftIcon, rightIcon;
    private String leftTxt, rightTxt;
    private int leftTextColor, rightTextColor;
    private float leftTextSize, rightTextSize;

    public ItemLayout(Context context) {
        super(context);
        this.context = context;
        initView(null);
    }


    public ItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public ItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    public void setRightIcon(int icon) {
        if (icon != 0) {
            rightIconImg.setVisibility(VISIBLE);
            rightIconImg.setImageResource(icon);
        } else {
            rightIconImg.setVisibility(GONE);
        }
        this.invalidate();
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
        leftIcon = typedArray.getResourceId(R.styleable.ItemLayout_leftIcon, -1);
        rightIcon = typedArray.getResourceId(R.styleable.ItemLayout_rightIcon, -1);
        leftTxt = typedArray.getString(R.styleable.ItemLayout_leftTitleTxt);
        rightTxt = typedArray.getString(R.styleable.ItemLayout_rightTitleTxt);
        leftTextColor = typedArray.getColor(R.styleable.ItemLayout_leftTitleTextColor, getResources().getColor(R.color.color_333333));
        rightTextColor = typedArray.getColor(R.styleable.ItemLayout_rightTitleTextColor, getResources().getColor(R.color.color_666666));
        leftTextSize = typedArray.getFloat(R.styleable.ItemLayout_leftTitleTextSize, 15);
        rightTextSize = typedArray.getFloat(R.styleable.ItemLayout_rightTitleTextSize, 13);
        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, null);
        leftIconImg = view.findViewById(R.id.leftIconImg);
        rightIconImg = view.findViewById(R.id.rightIconImg);
        leftTitleTxt = view.findViewById(R.id.leftTitleTxt);
        rightTitleTxt = view.findViewById(R.id.rightTitleTxt);
        this.addView(view);

        if (leftIcon != -1) {
            leftIconImg.setVisibility(VISIBLE);
            leftIconImg.setImageResource(leftIcon);
        } else {
            leftIconImg.setVisibility(GONE);
        }

        if (rightIcon != -1) {
            rightIconImg.setVisibility(VISIBLE);
            rightIconImg.setImageResource(rightIcon);
        } else {
            rightIconImg.setVisibility(GONE);
        }

        if (TextUtils.isEmpty(leftTxt)) {
            leftTitleTxt.setVisibility(INVISIBLE);
        } else {
            leftTitleTxt.setVisibility(VISIBLE);
            leftTitleTxt.setText(leftTxt);
            leftTitleTxt.setTextColor(leftTextColor);
            leftTitleTxt.setTextSize(leftTextSize);
        }


        if (TextUtils.isEmpty(rightTxt)) {
            rightTitleTxt.setVisibility(INVISIBLE);
        } else {
            rightTitleTxt.setVisibility(VISIBLE);
            rightTitleTxt.setText(rightTxt);
            rightTitleTxt.setTextColor(rightTextColor);
            rightTitleTxt.setTextSize(rightTextSize);
        }


    }
}
