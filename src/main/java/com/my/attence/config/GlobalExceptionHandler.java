package com.my.attence.config;

import com.my.attence.common.R;
import com.my.attence.common.code.BaseResponseCode;
import com.my.attence.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Created by BlueT on 2017/3/4.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("Exception,exception:{}", e, e);
        return R.fail(BaseResponseCode.SYSTEM_BUSY.getMsg());
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    R businessExceptionHandler(BusinessException e) {
        log.error("Exception,exception:{}", e, e);
        return R.fail(e.getDetailMessage());
    }

    /**
     * 没有权限 返回403视图
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public R errorPermission(AuthorizationException e) {
        log.error("Exception,exception:{}", e, e);
        return R.fail(BaseResponseCode.UNAUTHORIZED_ERROR.getMsg());

    }

    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return R.fail(errors.get(0).getDefaultMessage());
    }

}
