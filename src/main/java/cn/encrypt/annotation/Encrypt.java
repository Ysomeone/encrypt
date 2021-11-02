package cn.encrypt.annotation;


import cn.encrypt.entity.None;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuan
 * @Description: 加密注解
 * @date 2021/10/30 17:22
 * @version: 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
    /***
     * 请求参数的类名（不传则表示不需要传数据）
     *
     * @return
     */
    Class requestClass() default None.class;

    /**
     * 向加密后返回响应码填充的名称
     *
     * @return
     */
    String codeName() default "code";

    /**
     * 向加密后返回响应信息填充值的名称
     *
     * @return
     */
    String messageName() default "message";
}
