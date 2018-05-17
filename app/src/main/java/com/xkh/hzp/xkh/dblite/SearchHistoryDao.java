package com.xkh.hzp.xkh.dblite;

import android.content.Context;

import com.xkh.hzp.xkh.entity.SearchHistoryBean;

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
}
