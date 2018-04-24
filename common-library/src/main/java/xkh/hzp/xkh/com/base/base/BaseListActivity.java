package xkh.hzp.xkh.com.base.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import xkh.hzp.xkh.com.R;

/**
 * Author 唐洋
 * Date 2018/4/24
 * 描述
 **/
public abstract class BaseListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycleView);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.swiperRefreshThemeColor));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        setViewTheme();

        setAdapter();

        initData();

    }

    protected abstract void setViewTheme();

    protected abstract void setAdapter();

    protected abstract void initData();

    @Override
    public void setListenner() {
        swipeRefreshLayout.setOnRefreshListener(this);

    }

}
