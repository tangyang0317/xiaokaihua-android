package com.xkh.hzp.xkh.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2017/9/8.
 */

public class AbHttpEntity {

    private String str;
    private String code;
    private String msg;
    private boolean success;

    /**
     * 字段名与字段类型
     */
    public HashMap<String, Type> types = new HashMap<>();

    /**
     * 特殊字段保存
     */
    public HashMap<String, Object> extras = new HashMap<>();

    public AbHttpEntity(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public HashMap<String, Object> getExtras() {
        return extras;
    }

    public AbHttpEntity putField(String str, Type type) {
        types.put(str, type);
        return this;
    }

    public void parseFields(String jsonStr) {
        JsonElement jsonResp = new JsonParser().parse(jsonStr);
        if (jsonResp.isJsonObject()) {
            JsonObject obj = jsonResp.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                if ("code".equals(entry.getKey())) {
                    code = obj.get("code").getAsString();
                } else if ("msg".equals(entry.getKey())) {
                    msg = obj.get("msg").getAsString();
                } else if ("success".equals(entry.getKey())) {
                    success = obj.get("success").getAsBoolean();
                } else {
                    if (types.containsKey(entry.getKey())) {
                        Object valueObj = new Gson().fromJson(entry.getValue(), types.get(entry.getKey()));
                        extras.put(entry.getKey(), valueObj);
                    }
                }
            }
        }
    }

}
