package xkh.hzp.xkh.com.base.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xkh.hzp.xkh.com.R;

/**
 * @packageName xkh.hzp.xkh.com.base.view
 * @FileName EmptyView
 * @Author tangyang
 * @DATE 2018/5/23
 **/
public class EmptyView extends LinearLayout implements View.OnClickListener {

    private ImageView noDataImg;
    private TextView noDataTxt;
    private TextView noDataOperateTxt;
    private LoginClickListener loginClickListener;

    public EmptyView(Context context) {
        super(context);
        initView(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_no_data, null);
        noDataImg = (ImageView) view.findViewById(R.id.fragment_dormitory_empty_iv);
        noDataTxt = (TextView) view.findViewById(R.id.fragment_dormitory_empty_txt);
        noDataOperateTxt = (TextView) view.findViewById(R.id.fragment_focus_tv);
        noDataOperateTxt.setOnClickListener(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);
        setOrientation(VERTICAL);
        this.addView(view);
    }

    public void setOperateBtnVisiable(boolean isVisiable) {
        if (isVisiable) {
            noDataOperateTxt.setVisibility(VISIBLE);
        } else {
            noDataOperateTxt.setVisibility(GONE);
        }
    }

    public void setNodataTitle(String title) {
        noDataTxt.setText(title);
    }


    public void setNodataImageSource(int ResourceID) {
        noDataImg.setImageResource(ResourceID);
    }


    @Override
    public void onClick(View view) {
        if (view == noDataOperateTxt) {
            loginClickListener.loginCilck();
        }
    }


    public void setLoginClickListener(LoginClickListener login) {
        this.loginClickListener = login;
    }


    public interface LoginClickListener {
        void loginCilck();
    }
}
