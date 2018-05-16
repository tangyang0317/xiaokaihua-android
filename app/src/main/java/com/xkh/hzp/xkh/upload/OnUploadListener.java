package com.xkh.hzp.xkh.upload;

import java.util.HashMap;
import java.util.List;

/**
 * 主线程异步回调接口
 * Created by tangyang on 17/12/26.
 */

public interface OnUploadListener {

    void onAllSuccess(List<HashMap<String, Object>> allImages);

    void onAllFailed(String message);

    void onThreadFinish(int position);

}
