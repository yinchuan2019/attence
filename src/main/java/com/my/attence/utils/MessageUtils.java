package com.my.attence.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * json转换工具
 * Created by Administrator on 2017/3/13 013.
 */
public class MessageUtils {
    public static Locale locale;

    public static ResourceBundle bundle = ResourceBundle.getBundle("messages/message_zh_CN");

    @Autowired
    private  MessageSourceProperties messageSourceProperties;

    @Autowired
    private static MessageSource messageSource;


    public static void main(String args[]) {
        String message = bundle.getString("login.user");
        System.out.println(message);
        //System.out.println(messageSource.getMessage("cancel", null, Locale.getDefault()));
    }


}
