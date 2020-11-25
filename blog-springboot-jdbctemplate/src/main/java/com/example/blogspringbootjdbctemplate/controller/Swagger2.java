package com.example.blogspringbootjdbctemplate.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 付疆疆
 * @date 2020/11/25 8:38
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    //  http://127.0.0.1:8080/swagger-ui.html
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建api的基本信息
                .apiInfo(apiInfo())
                // 选择哪些接口去暴露
                .select()
                // 扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用jdbcTemplate的增删该查")
                .description("第一个jdbcTemplate")
                .termsOfServiceUrl("https://tenpaper.github.io/")
                .contact("tenpaper")
                .version("1.0")
                .build();
    }
}
