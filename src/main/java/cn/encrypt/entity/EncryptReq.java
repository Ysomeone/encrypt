package cn.encrypt.entity;

/**
 * @author yuan
 * @Description: 请求参数
 * @date 2021/10/30 17:41
 * @version: 1.0.0
 */
public class EncryptReq {

    /**
     * AES随机密码加密后的请求数据
     */
    private String data;

    /**
     * 用RSA公钥加密的AES密码
     */
    private String key;

    /**
     * 用客户端私钥签名的请求参数（签名的字符串【有值的字段才参与签名】，比如data=1&key=1&requestId=1&timestamp=12312）
     */
    private String sign;

    /**
     * 后端aop传输到接口的解密后的数据（不需要前端传）
     */
    private Object object;

    /**
     * 时间戳（非必输）
     */
    private Long timestamp;

    /**
     * 请求id（非必输）
     */
    private String requestId;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
