package xkh.hzp.xkh.com.base;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * BaseActivity
 * 封装ToolBar
 * StatusBar
 * Created by tangyang on 18/4/21.
 */

public class BaseActivty extends AppCompatActivity {

    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
