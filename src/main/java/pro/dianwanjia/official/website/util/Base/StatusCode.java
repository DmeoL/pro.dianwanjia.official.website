/**
 * @(#)RCSManageStatusCode 1.0 2018/7/13
 * @Copyright: Copyright 2007 - 2018 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: Modification History:
 * Date:        2018/7/13
 * Author:      sunsz
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:
 * Review Date:
 */
package pro.dianwanjia.official.website.util.Base;


import pro.dianwanjia.official.website.exception.GlobalStatusCode;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description:
 * @version V1.0.0
 */
public enum StatusCode implements GlobalStatusCode {

    RESULT_SUCCESS("00000000", "SUCCESS");


    private String statusCode;
    private String message;

    StatusCode(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
