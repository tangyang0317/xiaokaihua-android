package com.xkh.hzp.xkh.upload;

import java.util.HashMap;

/**
 * 任务线程回调接口
 * Created by tangyang on 17/12/26.
 */

public interface OnThreadResultListener {

    void onProgressChange(int percent);//进度变化回调

    void onFinish(HashMap<String, Object> imgObj);//线程完成时回调

    void onFail(String message);//发生错误时回调
}
