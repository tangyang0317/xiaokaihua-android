package com.xkh.hzp.xkh.upload;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 上传图片管理类
 * 获取七牛Token
 * 压缩图片，上传图片，拿到图片的地址
 * Created by tangyang on 17/12/25.
 */

public class UploadImageManager {
    private final static int THREAD_FINISH_CODE = 100;//线程完成
    private final static int THREAD_ALL_SUCCESS_CODE = 101;//所有线程完成
    private final static int THREAD_ALL_FAILED_CODE = 102;//所有线程执行失败
    private final static int THREAD_TOKEN_FAILED_CODE = 103;//获取token出错
    private final static int THREAD_COMPRESS_FAILED_CODE = 104;//图片压缩出错
    private final static String THREAD_POSITION = "THREAD_POSITION";
    private int threadCount = 0;//任务数量
    private ExecutorService executor;//线程池
    private CountDownLatch downLatch;//计数器
    private static String QINIU_TOKEN;
    private Context context;
    private List<String> filePath;
    private UploadHandler uploadHandler;
    private OnUploadListener onUploadListener;
    private static List<HashMap<String, Object>> uploadFinished;

    public static UploadImageManager getInstances() {
        return new UploadImageManager();
    }

    public void doUpload(Context context, List<String> filePath, OnUploadListener uploadListener) {
        this.context = context;
        this.filePath = filePath;
        this.onUploadListener = uploadListener;
        uploadHandler = new UploadHandler(this);
        getToken();
    }


    /**
     * 获取七牛Token，图片的cdn域名地址
     */
    private void getToken() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "annex_image");
        ABHttp.getIns().get(UrlConfig.getToken, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", String.class);
            }

            @Override
            public boolean onFailure(String code, String msg) {
                Bundle bundle = new Bundle();
                bundle.putInt(THREAD_POSITION, -1);
                Message.obtain(uploadHandler, THREAD_TOKEN_FAILED_CODE, bundle).sendToTarget();
                return true;
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                if (success) {
                    QINIU_TOKEN = (String) extra.get("result");
                    comressUploadImage();
                } else {
                    uploadHandler.sendEmptyMessage(THREAD_TOKEN_FAILED_CODE);
                }
            }


        });

    }


    /**
     * 图片压缩，鲁班压缩
     */
    private void comressUploadImage() {
        threadCount = filePath.size();
        downLatch = new CountDownLatch(threadCount);
        executor = Executors.newFixedThreadPool(1);
        uploadFinished = new ArrayList<>();
        new Thread(new UploadListener(downLatch, new OnAllThreadResultListener() {//创建一个监听线程
            @Override
            public void onSuccess() {
                uploadHandler.sendEmptyMessage(THREAD_ALL_SUCCESS_CODE);
            }

            @Override
            public void onFailed() {
                uploadHandler.sendEmptyMessage(THREAD_ALL_FAILED_CODE);
            }
        })).start();

        for (int i = 0; i < filePath.size(); i++) {
            Logger.d("本地图片----" + Thread.currentThread().getId() + "----->" + filePath.get(i));
            startUpload(filePath.get(i), i);
        }
    }

    /***
     * 压缩完成，开始上传图片
     * @param filePath
     * @param finalI
     */

    private void startUpload(String filePath, final int finalI) {
        final Bundle bundle = new Bundle();
        bundle.putInt(THREAD_POSITION, finalI);
        executor.submit(new UploadFile(context, downLatch, filePath, QINIU_TOKEN, new OnThreadResultListener() {
            @Override
            public void onProgressChange(int percent) {

            }

            @Override
            public void onFinish(HashMap<String, Object> imgObj) {
                Logger.d("完成上传后-----" + Thread.currentThread().getId() + "---->" + imgObj.get("url"));
                uploadFinished.add(imgObj);
                Message.obtain(uploadHandler, THREAD_FINISH_CODE, bundle).sendToTarget();
            }

            @Override
            public void onFail(String message) {
                Message.obtain(uploadHandler, THREAD_ALL_FAILED_CODE, bundle).sendToTarget();
            }
        }));
    }


    /**
     * 处理消息的handler
     */
    private static class UploadHandler extends Handler {
        private WeakReference<UploadImageManager> weakReference;

        private UploadHandler(UploadImageManager object) {
            super(Looper.getMainLooper());//执行在UI线程
            weakReference = new WeakReference<>(object);
        }

        @Override
        public void handleMessage(Message msg) {
            UploadImageManager uploadUtil = weakReference.get();
            if (uploadUtil != null) {
                Bundle data = (Bundle) msg.obj;
                int position;
                switch (msg.what) {
                    case THREAD_FINISH_CODE:
                        position = data.getInt(THREAD_POSITION);
                        uploadUtil.onUploadListener.onThreadFinish(position);
                        break;
                    case THREAD_ALL_SUCCESS_CODE:
                        uploadUtil.onUploadListener.onAllSuccess(uploadFinished);
                        uploadUtil.executor.shutdown();
                        break;
                    case THREAD_ALL_FAILED_CODE:
                        uploadUtil.onUploadListener.onAllFailed("图片上传失败");
                        uploadUtil.executor.shutdown();
                        break;
                    case THREAD_COMPRESS_FAILED_CODE:
                        uploadUtil.onUploadListener.onAllFailed("图片压缩出错");
                        uploadUtil.executor.shutdown();
                        break;
                    case THREAD_TOKEN_FAILED_CODE:
                        uploadUtil.onUploadListener.onAllFailed("获取七牛Token出错");
                        uploadUtil.executor.shutdown();
                        break;
                }
            }
        }
    }

}
