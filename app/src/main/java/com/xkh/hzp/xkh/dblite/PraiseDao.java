package com.xkh.hzp.xkh.dblite;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * @packageName com.xkh.hzp.xkh.dblite
 * @FileName PraiseDao
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class PraiseDao<T> extends BaseDao {
    /**
     * @param sqliteOpenHelper using this to get instance of  DatabaseHelper
     */
    public PraiseDao(OrmLiteSqliteOpenHelper sqliteOpenHelper) {
        super(sqliteOpenHelper);
    }
}
