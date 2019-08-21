package pro.dianwanjia.official.website.util.Base;

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import pro.dianwanjia.official.website.exception.GlobalStatusCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: 返回实体类
 * @version V1.0.0
 */
@XmlRootElement
@ToString
public class ResultVO<T> extends BaseObject implements Serializable {

    /**
     * 
     */
    private static final String RESULT_SUCCESS = "00000000";
    private static final long serialVersionUID = 7237821738979926948L;

    private String resultCode = RESULT_SUCCESS;

    private String resultMsg = "SUCCESS";

    private T data;

    public ResultVO() {
    }

    public ResultVO(GlobalStatusCode globalStatusCode) {
        this(globalStatusCode, null);
    }

    public ResultVO(String statusCode, String message) {
        this(statusCode, message, null);
    }

    public ResultVO(GlobalStatusCode globalStatusCode, T data) {
        this(globalStatusCode.getStatusCode(), globalStatusCode.getMessage(), data,null);
    }

    /**
     *
     * @param globalStatusCode
     * @param data
     * @param s message 中可以存在{}占位符，通过此参数按照顺序逐个替换
     */
    public ResultVO(GlobalStatusCode globalStatusCode, T data, String... s) {
        this(globalStatusCode.getStatusCode(), globalStatusCode.getMessage(), data,s);
    }

    public ResultVO(String resultCode, String resultMsg, T data) {
        this.data = data;
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
    }

    public ResultVO(String resultCode, String resultMsg, T data, String... s) {
        this.data = data;
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
        if(s != null){
            for(String _s :s){
                this.resultMsg = this.resultMsg.replace("{}",_s);
            }
        }
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatusCode(GlobalStatusCode statusCode) {
        this.resultCode = statusCode.getStatusCode();
        this.resultMsg = statusCode.getMessage();
    }

    public boolean hasError() {
        return !StringUtils.equalsIgnoreCase(this.getResultCode(), RESULT_SUCCESS);
    }
}
