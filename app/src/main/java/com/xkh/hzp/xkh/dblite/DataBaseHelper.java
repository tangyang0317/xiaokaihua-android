package com.xkh.hzp.xkh.dblite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xkh.hzp.xkh.entity.SearchHistoryBean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @packageName com.xkh.hzp.xkh.dblite
 * @FileName DataBaseHelper
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATA_BASE_NAME = "xkh.db";
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private static DataBaseHelper instance;

    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, SearchHistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DataBaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DataBaseHelper.class) {
                if (instance == null)
                    instance = new DataBaseHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws java.sql.SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
