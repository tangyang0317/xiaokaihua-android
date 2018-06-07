package com.xkh.hzp.xkh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;

/**
 * @packageName com.xkh.hzp.xkh.view
 * @FileName LoadMoreExpandableListView
 * @Author tangyang
 * @DATE 2018/6/7
 **/
public class SExpandableListView extends CommentExpandableListView implements AbsListView.OnScrollListener {
    /**
     * 是否支持加载更多
     */
    private boolean loadingMoreEnabled = true;

    private LoadingListener mLoadingListener;
    private View loadMoreView;
    private int lastY;
    private boolean isNoMore;
    private ProgressBar loadMorePb;
    private TextView loadMoreDesc;


    public SExpandableListView(Context context) {
        this(context, null);
    }

    public SExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        initSE(context);
        setOnScrollListener(this);
    }

    private void initSE(Context context) {
        /**
         * 这里是footer的填充,注意指定他的父亲为当前的listview,
         * 这里footer不用指定layoutparem是因为footer 在填充的时候已经指定了他的父view
         */
        loadMoreView = LayoutInflater.from(context).inflate(R.layout.item_footer_view, this, false);
        loadMorePb = (ProgressBar) loadMoreView.findViewById(R.id.pb_loading);
        loadMoreDesc = (TextView) loadMoreView.findViewById(R.id.tv_loadmore_desc);
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        if (loadingMoreEnabled) {
            addFooterView(loadMoreView);
        }
        super.setAdapter(adapter);
    }

    /**
     * 没有更多了
     *
     * @param noMore
     */
    public void setNoMore(boolean noMore) {
        this.isNoMore = noMore;
        noMoreAction();
    }

    private void noMoreAction() {
        loadMorePb.setVisibility(GONE);
        loadMoreDesc.setText("没有更多数据了");

    }

    public void loadMoreComp() {
        loadMorePb.setVisibility(GONE);
        loadMoreDesc.setText("没有更多数据了");
    }

    private void loadMoreAction() {
        loadMorePb.setVisibility(VISIBLE);
        loadMoreDesc.setText("正在加载...");
        if (mLoadingListener != null) {
            mLoadingListener.onLoadMore();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                Logger.d("停止滑动-->", "getLastVisiblePosition-->" + getLastVisiblePosition() + "-->getAdapter().getCount()" + getAdapter().getCount());
                if (loadingMoreEnabled && getLastVisiblePosition() == getAdapter().getCount() - 1) {
                    if (isNoMore) {
                        noMoreAction();
                    } else {
                        loadMoreAction();
                    }
                }
                break;
            case OnScrollListener.SCROLL_STATE_FLING:
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    /**
     * 加载更多回调
     */
    public interface LoadingListener {
        void onLoadMore();
    }


    public void setmLoadingListener(LoadingListener mLoadingListener) {
        this.mLoadingListener = mLoadingListener;
    }

    /**
     * 设置是否支持加载更多
     *
     * @param loadingMoreEnabled
     */
    public void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        this.loadingMoreEnabled = loadingMoreEnabled;
    }
}
