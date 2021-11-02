package cn.encrypt.utils;

import cn.encrypt.entity.EncryptConfigKit;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * @author yuan
 * @Description: RSA加密工具
 * @date 2021/10/31 14:00
 * @version: 1.0.0
 */
public class RSAUtil {
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
    /**
     * 前端给的公钥（用途 1、验签前端发来的sign 2、响应值加密aes秘钥 ）
     */
    public static final String CLIENT_PUBLIC_KEY = EncryptConfigKit.getEncryptConfig().getClientPublicKey();


    /**
     * 服务端的私钥（用途： 1、 解密前端发来的data【服务端公钥加密的aes随机密码 】
     * 2、加密响应参数的sign（提供给前端验签使用 ）
     */
    public static final String SERVER_PRIVATE_KEY = EncryptConfigKit.getEncryptConfig().getServerPrivateKey();


    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = EncryptConfigKit.getEncryptConfig().getSignAlgorithms();


    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException 异常
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();


        //得到公钥字符串
        System.out.println("公钥：" + BASE64_ENCODER.encodeToString(publicKey.getEncoded()));
        // 得到私钥字符串
        System.out.println("私钥：" + BASE64_ENCODER.encodeToString((privateKey.getEncoded())));
    }




    /**
     * RSA签名
     *
     * @param priKeyStr 商户私钥
     * @param srcStr    待签名数据
     * @return 签名值
     */
    public static String sign(String priKeyStr, String srcStr) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(BASE64_DECODER.decode(priKeyStr));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(srcStr.getBytes("UTF-8"));
        byte[] signed = signature.sign();
        return BASE64_ENCODER.encodeToString(signed);
    }

    /**
     * RSA-SHA1公钥验签
     *
     * @param publicKeyStr RSA公钥字符串
     * @param srcStr       原文字符串
     * @param signStr      签名字符串
     * @return true.有效的签名，false.无效的签名
     */
    public static boolean verifySign(String publicKeyStr, String srcStr, String signStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BASE64_DECODER.decode(publicKeyStr);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(srcStr.getBytes("UTF-8"));
            return signature.verify(BASE64_DECODER.decode(signStr));
        } catch (Exception e) {
            //who cares
        }
        return false;
    }


    /**
     * RSA公钥加密
     *
     * @param str 加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(CLIENT_PUBLIC_KEY, str);
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String publicKey, String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = BASE64_DECODER.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return BASE64_ENCODER.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
    }


    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = BASE64_DECODER.decode(str);
        //base64编码的私钥
        byte[] decoded = BASE64_DECODER.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte), "UTF-8");
    }

    public static boolean verifySign(String srcStr, String signStr) {
        return verifySign(CLIENT_PUBLIC_KEY, srcStr, signStr);
    }
}
