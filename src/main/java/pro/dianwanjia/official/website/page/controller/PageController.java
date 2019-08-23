package pro.dianwanjia.official.website.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author LX
 * @date: 2019/8/24 1:25
 * @description: 
 * @version V1.0.0
 */
@Controller
public class PageController {

    @GetMapping("/product")
    public String product(){
        return "product";
    }
    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
