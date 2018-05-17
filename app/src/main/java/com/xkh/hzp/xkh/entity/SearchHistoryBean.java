package com.xkh.hzp.xkh.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 搜索历史记录对象关系表
 * Created by tangyang on 17/12/29.
 */

@DatabaseTable(tableName = "search_history")
public class SearchHistoryBean {

    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
