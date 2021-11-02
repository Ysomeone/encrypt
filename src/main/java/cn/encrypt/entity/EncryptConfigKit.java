package cn.encrypt.entity;


/**
 * @author ：yuan
 * @description：加密参数配置工具
 * @date ：2021/11/1 10:38
 */
public class EncryptConfigKit {

    public static EncryptConfig config = null;

    public static EncryptConfig setEncryptConfig(EncryptConfig con) {
        if (con == null) {
            throw new IllegalStateException("配置类不能为空");
        }
        config = con;
        return config;
    }

    public static EncryptConfig getEncryptConfig() {
        if(config==null){
            throw new IllegalStateException("请先赋值");
        }
        return config;
    }
}

