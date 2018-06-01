package com.xkh.hzp.xkh.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xkh.hzp.xkh.MainActivity;
import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName WebActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class WebActivity extends BaseActivity {
    private WebView webView;

    public static void launchWebActivity(Context context, String url, String title, String backOfPage) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("backOfPage", backOfPage);
        context.startActivity(intent);
    }


    public String getUrl() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("url");
        }
        return null;
    }

    public String getTitles() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("title");
        }
        return null;
    }

    public String getBackOfPage() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("backOfPage");
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if ("Main".contains(getBackOfPage())) {
                    MainActivity.lunchActivity(WebActivity.this, null, MainActivity.class);
                    finish();
                } else {
                    finish();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void initView() {
        webView = findViewById(R.id.activity_webview);
        webView.loadUrl(getUrl().startsWith("http://") ? getUrl() : "http://" + getUrl());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setToolbarTitleTv(TextUtils.isEmpty(view.getTitle()) ? getTitles() : view.getTitle());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void callbackOnClickNavigationAction(View view) {
        if ("Main".contains(getBackOfPage())) {
            MainActivity.lunchActivity(WebActivity.this, null, MainActivity.class);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void setListenner() {

    }
}
