/**
 * @(#)GUIDUtil.java 1.0 2017年5月23日
 * @Copyright:  Copyright 2007 - 2017 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2017年5月23日
 * Author:      sunsz
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package pro.dianwanjia.official.website.util;

import java.util.UUID;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: UUID生成类
 * @version V1.0.0
 */
public class GUIDUtils {
    
    /**
     * 生成唯一标识 UUID
     * @return 去掉"-"的UUID
     */
    public static String generateGUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

