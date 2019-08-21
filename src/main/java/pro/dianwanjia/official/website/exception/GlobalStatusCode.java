package pro.dianwanjia.official.website.exception;


/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: 错误码统一接口，各服务实现其接口，详见{@code RcsManageStatusCode}
 * @version V1.0.0
 */
public interface GlobalStatusCode {
    
    /**
     * 获取状态码
     * @return
     */
    String getStatusCode();

    /**
     * 获取消息
     * @return
     */
    String getMessage();

}
