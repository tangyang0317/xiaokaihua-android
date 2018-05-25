package xkh.hzp.xkh.com.base.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import xkh.hzp.xkh.com.R;

/**
 * 加载弹出框
 *
 * @packageName xkh.hzp.xkh.com.base.view
 * @FileName UILoadingView
 * @Author tangyang
 * @DATE 2018/5/23
 **/
public class UILoadingView extends ProgressDialog {

    private boolean cancleable = false;
    private String noticeTitle = "正在加载数据";
    private TextView tvLoading;

    public UILoadingView(Context context) {
        super(context, R.style.LoadingDialog);
    }

    public UILoadingView(Context context, boolean cancleable, String noticeTxt) {
        super(context, R.style.LoadingDialog);
        this.cancleable = cancleable;
        this.noticeTitle = noticeTxt;
    }

    public void setNoticeTitle(String title) {
        tvLoading.setText(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(cancleable);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.view_loading);
        tvLoading = findViewById(R.id.tv_load_dialog);
        tvLoading.setText(noticeTitle);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
