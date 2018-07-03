package top.kittygirl.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {
    private boolean ret;
    private String msg;
    private Object data;
    //封装一些常用的构造方法
    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public static JsonData success(Object o, String m) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = o;
        jsonData.msg = m;
        return jsonData;
    }

    public static JsonData success(Object o) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = o;
        return jsonData;
    }

    public static JsonData success() {
        return new JsonData(true);
    }

    public static JsonData fail(String m) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = m;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }


}
