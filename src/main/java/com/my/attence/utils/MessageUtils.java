package com.my.attence.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * json转换工具
 * Created by Administrator on 2017/3/13 013.
 */
public class MessageUtils {
    public static Locale locale;

    public static ResourceBundle bundle;

    @Autowired
    public static MessageSourceProperties messageSourceProperties;


    static{
        bundle = ResourceBundle.getBundle("messages/message_zh_CN");
    }

    public static void main(String args[]) {
        String message = bundle.getString("cancel");
        //System.out.println(messageSourceProperties.getBasename());
    }


}
