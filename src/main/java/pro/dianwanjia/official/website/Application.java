package pro.dianwanjia.official.website;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author LX
 * @version V1.0.0
 * @date: 2019/8/20 12:03
 * @description:
 */
@Configuration
@SpringBootApplication
@MapperScan({"pro.dianwanjia.official.website.*.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
