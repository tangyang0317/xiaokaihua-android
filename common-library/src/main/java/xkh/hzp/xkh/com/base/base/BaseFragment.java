package xkh.hzp.xkh.com.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xkh.hzp.xkh.com.base.utils.ToastUtils;

/**
 * Fragment基类
 * Created by tangyang on 18/2/26.
 */

public abstract class BaseFragment extends Fragment {

    private View contentView;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public Context getFActivity() {
        return context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getFragmentLayoutId(), null);
        initView(contentView);
        setListernner();
        return contentView;
    }


    public void showToast(String toastMsg) {
        ToastUtils.showToast(context, toastMsg);
    }

    public void showLongToast(String toastMsg) {
        ToastUtils.showLongToast(context, toastMsg);
    }

    public abstract int getFragmentLayoutId();

    public abstract void initView(View contentView);

    public abstract void setListernner();


}
