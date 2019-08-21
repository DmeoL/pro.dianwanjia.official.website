package pro.dianwanjia.official.website.util.Base;


import com.fasterxml.jackson.annotation.JsonInclude;
import pro.dianwanjia.official.website.util.JsonUtils;

import java.io.Serializable;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description:
 * @version V1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseObject implements Serializable {

    @Override
    public String toString(){
        return JsonUtils.toJson(this);
    }
}
