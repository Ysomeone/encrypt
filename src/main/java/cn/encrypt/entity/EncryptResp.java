package cn.encrypt.entity;

import com.alibaba.fastjson.JSON;

/**
 * @author yuan
 * @Description: 加密后的响应参数
 * @date 2021/10/3017:44
 * @version: 1.0.0
 */
public class EncryptResp {
    /**
     * 返回码
     */
    private Integer code;

    /**
     * 使用请求参数里的128位AES随机密码 对响应数据进行加密
     */
    private String data;

    /**
     * 返回码解释
     */
    private String message;

    /**
     * 签名值
     */
    private String sign;

    /**
     * 使用前端公钥加密的对称公钥aes
     */
    private String key;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
