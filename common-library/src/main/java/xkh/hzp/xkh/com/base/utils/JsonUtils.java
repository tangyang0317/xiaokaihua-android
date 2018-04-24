package xkh.hzp.xkh.com.base.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class JsonUtils {
    public static final String TAG = JsonUtils.class.getName();

    /**
     * 描述：将对象转化为json.
     *
     * @return
     */
    public static String toJson(Object src) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param typeToken new TypeToken<ArrayList<?>>() {};
     * @return
     */
    public static List<?> fromJson(String json, TypeToken typeToken) {
        List<?> list = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = typeToken.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class clazz) {
        Object obj = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            obj = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * HashMap转json
     *
     * @param map
     * @return
     */
    public String mapToJson(HashMap<String, String> map) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 由初级类型组成的HashMap转JsonObject
     *
     * @param map
     * @return
     */
    public static JsonObject mapToJsonObj(Map<String, Object> map) {
        JsonParser parser = new JsonParser();
        JsonObject obj = new JsonObject();
        Map.Entry<String, Object> entry = null;
        String tempKey = null;
        Object tempObj = null;
        //遍历head
        for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry<String, Object>) iterator.next();
            tempKey = entry.getKey();
            tempObj = entry.getValue();
            if (tempObj == null) {
                obj.add(tempKey, JsonNull.INSTANCE);
            } else if (tempObj instanceof Number) {
                obj.addProperty(tempKey, (Number) tempObj);
            } else if (tempObj instanceof String) {
                obj.addProperty(tempKey, (String) tempObj);
            } else if (tempObj instanceof Boolean) {
                obj.addProperty(tempKey, (Boolean) tempObj);
            } else if (tempObj instanceof Character) {
                obj.addProperty(tempKey, (Character) tempObj);
            } else if (tempObj instanceof JSONArray) {
                JsonArray arr = new JsonArray();
                for (int i = 0; i < ((JSONArray) tempObj).length(); i++) {
                    try {
                        arr.add(parser.parse(((JSONArray) tempObj).getString(i)));
                    } catch (Exception e1) {
                        try {
                            arr.add(new JsonPrimitive(((JSONArray) tempObj).getString(i)));
                        } catch (JSONException e2) {
                            Log.d(TAG, "mapToJsonObj 转化失败！");
                        }
                    }
                }
                obj.add(tempKey, arr);
            } else if (tempObj instanceof JSONObject) {
                JsonElement ele = parser.parse(((JSONObject) tempObj).toString());
                obj.add(tempKey, ele);
            }
        }
        return obj;
    }

    /**
     * 描述：将json转化为hashmap列表.
     *
     * @param json
     * @return
     */
    public static HashMap<String, String> jsonToMapSS(String json) {
        HashMap<String, String> map = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            map = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 描述：将json转化为hashmap列表.
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> jsonToMapSO(String json) {
        HashMap<String, Object> map = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = new TypeToken<HashMap<String, Object>>() {
            }.getType();
            map = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 描述：将hashMap列表转化为json.
     *
     * @param list
     * @return
     */
    public static String mapListToJson(LinkedList<HashMap<String, String>> list) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将hashMap列表转化为json.
     *
     * @return
     */
    public static LinkedList<HashMap<String, String>> jsonToMapList(String jsonStr) {
        LinkedList<HashMap<String, String>> list = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = new TypeToken<LinkedList<HashMap<String, String>>>() {
            }.getType();
            list = gson.fromJson(jsonStr, type);
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public static LinkedList<String> jsonToStringList(String jsonStr) {
        LinkedList<String> list = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = new TypeToken<LinkedList<String>>() {
            }.getType();
            list = gson.fromJson(jsonStr, type);
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }
}



