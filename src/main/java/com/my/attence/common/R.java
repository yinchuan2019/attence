package com.my.attence.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import sun.util.locale.LocaleUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Created by abel on 2021/1/16
 * TODO
 */
@Data
@Component
public class R {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    private static ResourceBundleMessageSource resourceBundleMessageSource;

    @PostConstruct
    public void init(){
        R.resourceBundleMessageSource = this.messageSource;
    }

    /**
     * 请求响应code，0为成功 其他为失败
     */
    @ApiModelProperty(value = "请求响应code，0为成功 其他为失败", name = "code")
    private int code;

    /**
     * 响应异常码详细信息
     */
    @ApiModelProperty(value = "响应异常码详细信息", name = "msg")
    private String msg;

    @ApiModelProperty(value = "需要返回的数据", name = "data")
    private Object data;

    public R(int code, Object data) {
        this.code = code;
        this.data = data;
        this.msg=null;
    }

    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }


    public R() {
        this.code=0;
        this.msg="操作成功";
        this.data=null;
    }

    public R(Object data) {
        this.data = data;
        this.code= 0;
        this.msg="操作成功";
    }


    /**
     * 操作成功 data为null
     */
    public static R success(){
        return new R();
    }
    /**
     * 操作成功 data 不为null
     */
    public static R success(Object data){
        return new R(data);
    }

    /**
     * 操作失败 data 不为null
     */
    public static R fail(String msg){
        String temp = "";
        String temp1 = "";
        String result = "";
        try{
            if(msg.contains(":")){
                temp = msg.split(":")[0];
                temp1 = msg.split(":")[1];
                result = resourceBundleMessageSource.getMessage(temp, null, LocaleContextHolder.getLocale());
                result = result + ":" + temp1;
            }else {
                result = resourceBundleMessageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
            }
            return new R(1,result);
        }catch (MissingResourceException e){
            return new R(1,msg);
        }
    }
    /**
     *  自定义返回  data为null
     */
    public static R getResult(int code, String msg){
        return new R(code,msg);
    }



}
