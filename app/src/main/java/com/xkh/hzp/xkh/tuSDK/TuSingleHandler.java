package xkh.hzp.xkh.com.tuSDK;

/**
 * 单图操作回调
 * Created by tangyang on 17/11/23.
 */

public abstract class TuSingleHandler {


    abstract void onSingleTuSuccess(String filePath);


    abstract void onFail(String msg);
}
