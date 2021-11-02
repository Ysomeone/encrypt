package cn.encrypt.service;

/**
 * 验证时间戳和请求是否重复（实现该接口自行实现）
 */
public interface RequestVerify {
    /**
     * 验证时间戳和请求是否重复
     *
     * @param timestamp 时间戳
     * @param requestId 请求id
     */
    void requestVerify(Long timestamp, String requestId);

}
