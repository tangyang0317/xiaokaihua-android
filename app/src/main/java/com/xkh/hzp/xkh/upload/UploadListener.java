package com.xkh.hzp.xkh.upload;

import java.util.concurrent.CountDownLatch;

/**
 * 监听所有线程是否已经执行
 * Created by tangyang on 17/12/26.
 */

public class UploadListener implements Runnable {

    private CountDownLatch downLatch;
    private OnAllThreadResultListener listener;

    public UploadListener(CountDownLatch countDownLatch, OnAllThreadResultListener listener) {
        this.downLatch = countDownLatch;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            /******阻塞等待所有线程执行完成******/
            downLatch.await();
            listener.onSuccess();//顺利完成
        } catch (InterruptedException e) {
            listener.onFailed();
        }
    }
}
