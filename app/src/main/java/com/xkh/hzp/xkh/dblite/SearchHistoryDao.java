package com.xkh.hzp.xkh.dblite;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xkh.hzp.xkh.entity.SearchHistoryBean;

import java.sql.SQLException;

import xkh.hzp.xkh.com.base.Global;

/**
 * @packageName com.xkh.hzp.xkh.dblite
 * @FileName SearchHistoryDao
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchHistoryDao extends BaseDao<SearchHistoryBean> {

    /**
     * @param context using this to get instance of  DatabaseHelper
     */
    public SearchHistoryDao(Context context) {
        super(DataBaseHelper.getHelper(context));
    }


    /**
     * 添加一条搜索记录
     * 记录已经存在，直接删除
     */
    public void addSearchHistory(SearchHistoryBean searchHistoryBean) {
        deleteExitsSearchHistory(searchHistoryBean.getName());
        try {
            daoOpe.create(searchHistoryBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据名字删除搜索历史
     *
     * @return
     */
    public void deleteExitsSearchHistory(String name) {
        QueryBuilder queryBuilder = daoOpe.queryBuilder();
        try {
            SearchHistoryBean data = (SearchHistoryBean) queryBuilder.where().eq("name", name).queryForFirst();
            if (data != null) {
                daoOpe.delete(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
