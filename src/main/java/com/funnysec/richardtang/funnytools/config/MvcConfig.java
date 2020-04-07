package com.funnysec.richardtang.funnytools.config;

import com.funnysec.richardtang.funnytools.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringMVC配置
 *
 * @author RichardTang
 * @date 2020年3月15日21:17:10
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要拦截的路径规则
        List<String> includePathPatterns = new ArrayList<String>();
        includePathPatterns.add("/**");

        //不拦截的路径规则,注意放行静态资源
        List<String> excludePathPatterns = new ArrayList<String>();
        excludePathPatterns.add("/login**");
        excludePathPatterns.add("/captcha**");
        excludePathPatterns.add("/static/**");

        //注册拦截器
        registry.addInterceptor(getAuthInterceptor()).addPathPatterns(includePathPatterns).excludePathPatterns(excludePathPatterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
