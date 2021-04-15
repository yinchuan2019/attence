package com.my.attence.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * json转换工具
 * Created by Administrator on 2017/3/13 013.
 */

@Controller
public class MessageUtils {
    public static Locale locale = Locale.getDefault();

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public static ResourceBundle bundle = ResourceBundle.getBundle("messages/message_zh_CN");
    public static ResourceBundle bundle_ja = ResourceBundle.getBundle("messages/message_ja_JP");



    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public String test4(Locale locale) {

        System.out.println(messageSource.getMessage("login.login", null, Locale.JAPAN));
        return "i18n";
    }

}
