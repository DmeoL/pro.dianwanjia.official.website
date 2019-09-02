package pro.dianwanjia.official.website.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author LX
 * @version V1.0.0
 * @date: 2019/8/20 12:03
 * @description: swagger配置
 */
@EnableSwagger2
@Configuration
@Profile({"dev", "test", "default"})
@ConditionalOnClass(WebMvcConfigurer.class)
public class SwaggerMvcConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/swagger-ui").setViewName("redirect:/swagger-ui.html");
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/index.html").setViewName("/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName(appName).genericModelSubstitutes(DeferredResult.class)
                // .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false).forCodeGeneration(false).pathMapping("/").select()
                .apis(RequestHandlerSelectors.basePackage(appName)).paths(PathSelectors.any()).build()
                // 大标题
                .apiInfo(new ApiInfoBuilder().title(appName + " API")
                // 详细描述
                .description("点万家API接口服务")
                .build());
    }

}

