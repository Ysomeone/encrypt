package cn.encrypt.entity;

import cn.encrypt.service.RequestVerify;

import java.io.Serializable;

/**
 * @author ：yuan
 * @description：加密参数配置类
 * @date ：2021/11/1 10:16
 */
public class EncryptConfig implements Serializable {

    /**
     * 客户端公钥
     */
    private String clientPublicKey;

    /**
     * 服务端私钥
     */
    private String serverPrivateKey;

    /**
     * 签名算法
     */
    private String signAlgorithms;

    /**
     * 偏移量
     */
    private String initVector;

    private EncryptConfig() {
    }

    public static EncryptConfig builder() {
        return new EncryptConfig();
    }


    public EncryptConfig setClientPublicKey(String clientPublicKey) {
        if (clientPublicKey == null || "".equals(clientPublicKey)) {
            throw new IllegalStateException("clientPublicKey 不能为空");
        }
        this.clientPublicKey = clientPublicKey;
        return this;
    }

    public EncryptConfig setServerPrivateKey(String serverPrivateKey) {
        if (serverPrivateKey == null || "".equals(serverPrivateKey)) {
            throw new IllegalStateException("serverPrivateKey 不能为空");
        }
        this.serverPrivateKey = serverPrivateKey;
        return this;
    }

    public EncryptConfig setSignAlgorithms(String signAlgorithms) {
        if (signAlgorithms == null || "".equals(signAlgorithms)) {
            this.signAlgorithms = "SHA1WithRSA";
        } else {
            this.signAlgorithms = signAlgorithms;
        }
        return this;
    }

    public EncryptConfig setInitVector(String initVector) {
        if (initVector == null || "".equals(initVector)) {
            throw new IllegalStateException("initVector 不能为空");
        }
        this.initVector = initVector;
        return this;
    }

    public String getClientPublicKey() {
        if (clientPublicKey == null || "".equals(clientPublicKey)) {
            throw new IllegalStateException("clientPublicKey 不能为空");
        }
        return clientPublicKey;
    }

    public String getServerPrivateKey() {
        if (serverPrivateKey == null || "".equals(serverPrivateKey)) {
            throw new IllegalStateException("serverPrivateKey 不能为空");
        }
        return serverPrivateKey;
    }

    public String getSignAlgorithms() {
        if (signAlgorithms == null || "".equals(signAlgorithms)) {
            throw new IllegalStateException("signAlgorithms 不能为空");
        }
        return signAlgorithms;
    }

    public String getInitVector() {
        if (initVector == null || "".equals(initVector)) {
            throw new IllegalStateException("initVector 不能为空");
        }
        return initVector;
    }
}
