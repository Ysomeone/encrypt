package cn.encrypt.config;

import cn.encrypt.annotation.Encrypt;
import cn.encrypt.entity.EncryptReq;
import cn.encrypt.entity.EncryptResp;
import cn.encrypt.entity.None;
import cn.encrypt.service.RequestVerify;
import cn.encrypt.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yuan
 * @Description: 对请求参数和返回参数进行加密处理
 * @date 2021/10/30 17:36
 * @version: 1.0.0
 */
@Aspect
@Component
public class EncryptAspect {

    @Autowired(required = false)
    private RequestVerify requestVerify;

    @Around("@annotation(cn.encrypt.annotation.Encrypt)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Encrypt encrypt = method.getAnnotation(Encrypt.class);
        Object[] args = pjp.getArgs();
        EncryptReq param = null;
        for (Object arg : args) {
            if (arg instanceof EncryptReq) {
                param = (EncryptReq) arg;
            }
        }
        if (requestVerify != null) {
            requestVerify.requestVerify(param.getTimestamp(), param.getRequestId());
        }
        Class requestClass = encrypt.requestClass();
        Map map = EncryptUtil.decrypt(param);
        /**
         * 解密拿到前端传的数据并存在object对象做中转，接口从该对象拿到解密的数据
         */
        if (requestClass.equals(String.class)) {
            param.setObject(map.get("retStr").toString());
        } else if (requestClass.equals(None.class)) {
            param.setObject(null);
        } else {
            param.setObject(JSON.parseObject(map.get("retStr").toString(), requestClass));
        }
        Object[] aa = new Object[args.length];
        int i = 0;
        for (Object arg : args) {
            if (arg instanceof EncryptReq) {
                aa[i++] = param;
            } else {
                aa[i++] = arg;
            }
        }
        Object proceed = pjp.proceed(aa);
        JSONObject jsonObject = JSONObject.parseObject(proceed.toString());
        /**
         * 获取接口响应的响应码和响应信息，并填充到返回给前端code和message中
         */
        Integer code = null;
        String message = null;
        String codeName = encrypt.codeName();
        String messageName = encrypt.messageName();
        if (jsonObject.containsKey(codeName)) {
            code = (Integer) jsonObject.get(codeName);
        }
        if (jsonObject.containsKey(messageName)) {
            message = jsonObject.get(messageName).toString();
        }
        EncryptResp aes = EncryptUtil.encrypt(map.get("aes").toString(), jsonObject, code, message);
        return aes.toString();
    }


}
