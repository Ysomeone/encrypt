package cn.encrypt.utils;

import cn.encrypt.entity.EncryptReq;
import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author ：yuan
 * @description：获取签名字符串
 * @date ：2021/11/1 17:30
 */
public class SignUtil {
    /**
     * 拼接对象的字段
     *
     * @param o
     * @return
     */
    public static String getBeforeSignStr(Object o) {
        SortedMap<?, ?> parameters = new TreeMap<>(toMap(o));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : parameters.entrySet()) {
            if (!"sign".equals(entry.getKey()) && !"sig".equals(entry.getKey())) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static Map<?, ?> toMap(Object o) {
        return o == null ? null : JSON.parseObject(JSON.toJSONString(o), Map.class);
    }
}
