package xkh.hzp.xkh.com.http.base;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 返回一个String类型的Observer
 * Created by tangyang on 18/4/21.
 */

public abstract class BaseObserverForString implements Observer<String> {
    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(String s) {
        onRequestEnd();
        try {
            onSuccees(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }


    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
    }


    /**
     * 返回成功
     *
     * @throws Exception
     */
    protected abstract void onSuccees(String str) throws Exception;


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

}
