package com.xkh.hzp.xkh.upload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.entity.QiNiuBean;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import top.zibin.luban.Luban;

/**
 * 上传图片的线程
 * Created by tangyang on 17/12/26.
 */

public class UploadFile implements Runnable {

    private CountDownLatch downLatch;//计数器
    private String uploadFile;//文件名
    private OnThreadResultListener listener;//任务线程回调接口
    private static UploadManager uploadManager = null;
    private String token;
    private Context context;

    public UploadFile(Context context, CountDownLatch downLatch, String filePath, String token, OnThreadResultListener listener) {
        this.downLatch = downLatch;
        this.uploadFile = filePath;
        this.listener = listener;
        this.token = token;
        this.context = context;
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .build();
        if (uploadManager == null) {
            uploadManager = new UploadManager(config);
        }
    }


    /**
     * 链接七牛，开始上传图片
     */
    private void doUploadToQiNiu(final File file) {
        Logger.d("doUploadToQiNiu ------" + Thread.currentThread().getId() + "--------->" + file.getPath());
        synchronized (uploadManager) {
            uploadManager.put(file, (System.currentTimeMillis() * 100 * 1000) + ".jpg", token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    Logger.d("doUploadToQiNiu complete--------" + Thread.currentThread().getId() + "-------->" + file.getPath());
                    if (info.isOK()) {
                        String s = response.toString();
                        QiNiuBean sevenCowBean = new Gson().fromJson(s, QiNiuBean.class);
                        String key1 = sevenCowBean.getKey();
                        HashMap<String, Object> bitmap = new HashMap<>();
                        Bitmap localBitmap = BitmapFactory.decodeFile(file.getPath());
                        bitmap.put("url", Config.QI_NIU_DOMAIN + "/" + key1);
                        bitmap.put("width", localBitmap.getWidth());
                        bitmap.put("height", localBitmap.getHeight());
                        localBitmap.recycle();
                        /****计数，回调完成状态****/
                        downLatch.countDown();
                        listener.onFinish(bitmap);
                    } else {
                        listener.onFail(response.toString());
                    }
                    synchronized (uploadManager) {
                        uploadManager.notify();
                    }
                }

            }, null);
            try {
                uploadManager.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void run() {
        try {
            List<File> list = Luban.with(context)
                    .ignoreBy(100)
                    .load(uploadFile)
                    .get();
            Logger.d("压缩后的-----" + Thread.currentThread().getId() + "--->" + list.get(0).getPath());
            doUploadToQiNiu(list.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
