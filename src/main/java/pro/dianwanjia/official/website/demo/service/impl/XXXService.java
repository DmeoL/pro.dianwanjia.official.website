package pro.dianwanjia.official.website.demo.service.impl;

import pro.dianwanjia.official.website.demo.mapper.XXXMapper;
import pro.dianwanjia.official.website.demo.service.IXXXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LX
 * @version V1.0.0
 * @date: 2019/8/20 12:03
 * @description:
 */
@Service
public class XXXService implements IXXXService {


    @Autowired
    private XXXMapper xxxMapper;
 
}
