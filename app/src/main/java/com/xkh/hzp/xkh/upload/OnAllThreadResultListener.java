package com.xkh.hzp.xkh.upload;

/**
 * 监听线程回调接口
 * Created by tangyang on 17/12/26.
 */

public interface OnAllThreadResultListener {

    void onSuccess();//所有线程执行完毕

    void onFailed();//所有线程执行出现问题
}
