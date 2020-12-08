package com.my.attence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;



/**
 * Created by abel on 2020/12/9
 * TODO
 */
public class MessageConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver() {

        SessionLocaleResolver localeResolver = new SessionLocaleResolver();

        //设置默认区域
        localeResolver.setDefaultLocale(Locale.CHINESE); // 默认简化汉语
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("l");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());

    }
}
