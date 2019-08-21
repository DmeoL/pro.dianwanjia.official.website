package pro.dianwanjia.official.website.util.Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="日志")
public class LogModel extends BaseObject implements Serializable {

    @ApiModelProperty(value = "主键",example = "1")
    private Integer logId;

    @ApiModelProperty(value = "请求地址",example = "http://localhost:11000/dianwanjia/index")
    private String logUrl;

    @ApiModelProperty(value = "HTTP请求方式",example = "POST")
    private String logMethod;

    @ApiModelProperty(value = "客户端请求的IP地址",example = "0:0:0:0:0:0:0:1")
    private String logRequestIp;

    @ApiModelProperty(value = "传入参数",example = "xxxx")
    private String logInputParam;

    @ApiModelProperty(value = "传出参数",example = "{\"resultCode\":\"00000000\",\"resultMsg\":\"SUCCESS\",\"data\":\"xxx\"}")
    private String logOutputParam;

    @ApiModelProperty(value = "操作人",example = "xxx")
    private String optUserName;

    @ApiModelProperty(value = "更新时间",example = "1537518847764")
    private Long updateTime;

    @ApiModelProperty(value = "操作描述",example = "新增属性")
    private String logDesc;


    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl == null ? null : logUrl.trim();
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod == null ? null : logMethod.trim();
    }

    public String getLogRequestIp() {
        return logRequestIp;
    }

    public void setLogRequestIp(String logRequestIp) {
        this.logRequestIp = logRequestIp == null ? null : logRequestIp.trim();
    }

    public String getLogInputParam() {
        return logInputParam;
    }

    public void setLogInputParam(String logInputParam) {
        this.logInputParam = logInputParam == null ? null : logInputParam.trim();
    }

    public String getLogOutputParam() {
        return logOutputParam;
    }

    public void setLogOutputParam(String logOutputParam) {
        this.logOutputParam = logOutputParam == null ? null : logOutputParam.trim();
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName == null ? null : optUserName.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }
}