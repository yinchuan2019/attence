package com.my.attence.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 向mvc中添加自定义组件
 * Created by BlueT on 2017/3/9.
 */
@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource
    private LoginInterceptor baseInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**/index/**")
                .addPathPatterns("/**/user/**")

        ;
    }


  /**
     * 页面跨域访问Controller过滤
     */
   @Override
   public void addCorsMappings(CorsRegistry registry) {
       WebMvcConfig.super.addCorsMappings(registry);
       registry.addMapping("/**")
               .allowedHeaders("*")
               .allowedMethods("POST", "GET", "PUT", "DELETE")
               .allowedOrigins("*");
   }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/templates/");

        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }


}

