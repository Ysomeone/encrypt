package cn.encrypt.utils;


import cn.encrypt.entity.EncryptResp;
import cn.encrypt.entity.EncryptReq;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuan
 * @Description: 加解密工具类
 * @date 2021/10/31 14:05
 * @version: 1.0.0
 */
public class EncryptUtil {

    /**
     * 解密
     * @param param
     * @return
     * @throws Exception
     */
    public static Map decrypt(EncryptReq param) throws Exception {
        /**
         * 128位AES随机密码
         */
        String aes = RSAUtil.decrypt(param.getKey(), RSAUtil.SERVER_PRIVATE_KEY);

        /**
         * 解密后拿到的json数据
         */
        String retStr = AesCbcUtil.decrypt(aes, param.getData());
        /**
         * 签名数据
         */
        String beforeSignStr = SignUtil.getBeforeSignStr(param);
        /**
         * 验签是不是对的
         */
        boolean flag = RSAUtil.verifySign(beforeSignStr, param.getSign());

        if (!flag) {
            throw new IllegalStateException("验签失败");
        }
        Map<String, String> map = new HashMap<>();
        map.put("aes", aes);
        map.put("retStr", retStr);
        return map;
    }


    /**
     * 加密
     *
     * @param aes
     * @param object
     * @param code
     * @param message
     * @return
     * @throws Exception
     */
    public static EncryptResp encrypt(String aes, Object object, Integer code, String message) throws Exception {
        EncryptResp encryptResp = new EncryptResp();
        encryptResp.setCode(code);
        encryptResp.setData(AesCbcUtil.encrypt(aes, JSONObject.toJSONString(object)));
        encryptResp.setMessage(message);
        /**
         * 签名数据
         */
        String beforeSignStr = SignUtil.getBeforeSignStr(encryptResp);
        encryptResp.setSign(RSAUtil.sign(RSAUtil.SERVER_PRIVATE_KEY, beforeSignStr));
        encryptResp.setKey(RSAUtil.encrypt(RSAUtil.CLIENT_PUBLIC_KEY,aes));
        return encryptResp;
    }
}
