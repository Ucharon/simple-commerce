package asia.oxox.charon.simplecommerce.config;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @program: demo-code
 * @description:
 * @author: Charon
 * @create: 2023-04-09 16:09
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfiguration implements WebMvcConfigurer {
    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;

    /**
     * 接口调试地址
     */
    private String tryHost;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(enable)
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 接口调试地址
                .host(tryHost)

                // 选择哪些接口作为swagger的doc发布
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("cn.homyit.controller"))
                .build()

                // 支持的通讯协议集合
                .protocols(CollectionUtil.newHashSet("https", "http"));

        // 授权信息设置，必要的header token等认证信息
        //.securitySchemes(securitySchemes())

        // 授权信息全局应用
        //.securityContexts(securityContexts())

    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(applicationName + " Api Doc")
                .description(applicationDescription)
                .contact(new Contact("Homyit", "homyit.aday.top", "1303962785@qq.com"))
                .version("Application Version: " + applicationVersion + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    //@SuppressWarnings("unchecked")
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    try {
    //        Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
    //        List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
    //        if (registrations != null) {
    //            for (InterceptorRegistration interceptorRegistration : registrations) {
    //                interceptorRegistration
    //                        .excludePathPatterns("/swagger**/**")
    //                        .excludePathPatterns("/webjars/**")
    //                        .excludePathPatterns("/v3/**")
    //                        .excludePathPatterns("/doc.html");
    //            }
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}

}
