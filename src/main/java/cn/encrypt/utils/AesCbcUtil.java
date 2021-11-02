package cn.encrypt.utils;



import cn.encrypt.entity.EncryptConfigKit;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author yuan
 * @Description: 对称加密工具包
 * @date 2021/10/31 14:04
 * @version: 1.0.0
 */
public class AesCbcUtil {
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    private static String initVector = EncryptConfigKit.getEncryptConfig().getInitVector();

    /**
     * 加密
     *
     * @param key   解密密钥
     * @param value 待加密内容
     * @return 解密内容
     */
    public static String encrypt(String key, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return BASE64_ENCODER.encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key       解密密钥
     * @param encrypted 待解密内容
     * @return 解密内容
     */
    public static String decrypt(String key, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(BASE64_DECODER.decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }

}
