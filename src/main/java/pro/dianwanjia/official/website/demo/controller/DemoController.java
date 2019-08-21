package pro.dianwanjia.official.website.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.dianwanjia.official.website.demo.model.Tdemo;
import pro.dianwanjia.official.website.demo.service.IXXXService;
import pro.dianwanjia.official.website.util.Base.LogModel;
import pro.dianwanjia.official.website.util.Base.ResultVO;
import pro.dianwanjia.official.website.util.Base.StatusCode;

/**
 * @author LX
 * @date: 2019/8/21 0:27
 * @description: 
 * @version V1.0.0
 */
@RequestMapping("/demo")
@RestController
@Api(tags = "点万家demo")
public class DemoController {

    @Autowired
    private IXXXService ixxxService;

    @GetMapping("/index")
    @ApiOperation(value = "点万家")
    public ResultVO<Tdemo> hello(){
        return ixxxService.getTdemo();
    }

}
