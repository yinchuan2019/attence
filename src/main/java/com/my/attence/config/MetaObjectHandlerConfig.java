package com.my.attence.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.my.attence.service.HttpSessionService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;


/**
 * mybatis plus 默认值配置
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    @Lazy
    @Resource
    HttpSessionService httpSessionService;

    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentDate = new Date();
        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
        if (setterNameSet.contains("deleted")) {
            setFieldValByName("deleted", 1, metaObject);
        }
        if (setterNameSet.contains("createTime")) {
            setFieldValByName("createTime", currentDate, metaObject);
        }
        if (setterNameSet.contains("createDate")) {
            setFieldValByName("createDate", currentDate, metaObject);
        }
        if (setterNameSet.contains("createId")) {
            //setFieldValByName("createId", httpSessionService.getCurrentUserId(), metaObject);
        }
        if (setterNameSet.contains("updateId")) {
            //setFieldValByName("updateId", httpSessionService.getCurrentUserId(), metaObject);
        }
        if (setterNameSet.contains("updateTime")) {
            setFieldValByName("updateTime", currentDate, metaObject);
        }
        if (setterNameSet.contains("updateDate")) {
            setFieldValByName("updateDate", currentDate, metaObject);
        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentDate = new Date();
        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
        if (setterNameSet.contains("updateTime")) {
            setFieldValByName("updateTime", currentDate, metaObject);
        }
        if (setterNameSet.contains("updateDate")) {
            setFieldValByName("updateDate", currentDate, metaObject);
        }
        if (setterNameSet.contains("updateId")) {
            //setFieldValByName("updateId", httpSessionService.getCurrentUserId(), metaObject);
        }
    }
}