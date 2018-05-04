package com.xkh.hzp.xkh.tuSDK;

import java.util.List;

/**
 * Created by tangyang on 17/11/23.
 */

public interface TuMutipleHandle {

    void onMultipleTuSuccess(List<String> filePath);

    void onFail(String msg);

}
