package pro.dianwanjia.official.website;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LX
 * @version V1.0.0
 * @date: 2019/8/20 12:03
 * @description: 点万家官网
 */
@Configuration
@SpringBootApplication
@Controller
@ServletComponentScan
@MapperScan({"pro.dianwanjia.official.website.*.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("{page}")
    public String page(@PathVariable("page") String page){
        return page;
    }
}
