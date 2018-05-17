package com.xkh.hzp.xkh.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.dblite.SearchHistoryDao;
import com.xkh.hzp.xkh.entity.SearchHistoryBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SearchHistoryActivty
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchHistoryActivty extends BaseActivity {

    /*UI组件*/
    private EditText searchEdit;
    private TextView cancleSearchTxt;
    private ImageView cleanHistoryImg;
    private LinearLayout searchHistoryLayout;
    private TagFlowLayout historyTagFlowLayout;
    private List<SearchHistoryBean> searchHistoryBeanList;


    public static void openActivity(Context context, int index) {
        Intent intent = new Intent(context, SearchHistoryActivty.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
    }

    private int getIndex() {
        return getIntent().getIntExtra("index", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_history;
    }

    @Override
    public void initView() {
        hideToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        searchEdit = findViewById(R.id.searchEdit);
        cancleSearchTxt = findViewById(R.id.cancleSearchTxt);
        historyTagFlowLayout = findViewById(R.id.historyTagFlowLayout);
        searchHistoryLayout = findViewById(R.id.searchHistoryLayout);
        cleanHistoryImg = findViewById(R.id.cleanHistoryImg);
        searchEdit.setFocusable(true);
        searchEdit.setFocusableInTouchMode(true);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /***
                     * 搜索记录保存数据库
                     * 跳转搜索结果页面
                     */
                    String search = searchEdit.getText().toString().trim();
                    String searchHint = searchEdit.getHint().toString().trim();
                    SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                    searchHistoryBean.setName(TextUtils.isEmpty(search) ? searchHint : search);
                    SearchHistoryDao searchHistoryDao = new SearchHistoryDao(SearchHistoryActivty.this);
                    searchHistoryDao.createOrUpdate(searchHistoryBean);
                    queryHistoryData();

                    Intent intent = new Intent(SearchHistoryActivty.this, SearchActivity.class);
                    intent.putExtra("content", TextUtils.isEmpty(search) ? searchHint : search);
                    intent.putExtra("index", getIndex());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        cancleSearchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchHistoryActivty.this.finish();
            }
        });

        cleanHistoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SearchHistoryActivty.this);
                builder.setTitle("清空搜索记录");
                builder.setMessage("搜索记录清空后不能恢复,你确定要删除吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.show().dismiss();
                    }
                });
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SearchHistoryDao searchHistoryDao = new SearchHistoryDao(SearchHistoryActivty.this);
                        searchHistoryDao.clearAll();
                        queryHistoryData();
                    }
                });
                builder.show();
            }
        });

        historyTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                SearchHistoryBean searchHistoryBean = searchHistoryBeanList.get(position);
                if (searchHistoryBean != null) {
                    SearchHistoryDao searchHistoryDao = new SearchHistoryDao(SearchHistoryActivty.this);
                    searchHistoryDao.createOrUpdate(searchHistoryBean);
                    queryHistoryData();
                    Intent intent = new Intent(SearchHistoryActivty.this, SearchActivity.class);
                    intent.putExtra("content", searchHistoryBean.getName());
                    intent.putExtra("index", getIndex());
                    startActivity(intent);
                }
                return false;
            }
        });
        queryHistoryData();
    }

    /***
     * 查询历史数据
     * 设置UI页面
     */
    private void queryHistoryData() {
        SearchHistoryDao searchHistoryDao = new SearchHistoryDao(SearchHistoryActivty.this);
        searchHistoryBeanList = searchHistoryDao.all();
        if (searchHistoryBeanList != null && searchHistoryBeanList.size() > 0) {
            searchHistoryLayout.setVisibility(View.VISIBLE);
            historyTagFlowLayout.setAdapter(new TagAdapter<SearchHistoryBean>(searchHistoryBeanList) {
                @Override
                public View getView(FlowLayout parent, int position, SearchHistoryBean searchHistoryBean) {
                    TextView tv = (TextView) LayoutInflater.from(SearchHistoryActivty.this).inflate(R.layout.item_search_tag,
                            historyTagFlowLayout, false);
                    tv.setText(searchHistoryBean.getName());
                    return tv;
                }
            });
        } else {
            searchHistoryLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void setListenner() {

    }
}
