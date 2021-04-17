package com.my.attence.config;


import com.my.attence.entity.SysAdmin;
import com.my.attence.service.AdminService;
import com.my.attence.utils.AdminCommons;
import com.my.attence.utils.IPKit;
import com.my.attence.utils.MapCache;
import com.my.attence.utils.TaleUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * Created by BlueT on 2017/3/9.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Resource
    private AdminService adminService;

    private MapCache cache = MapCache.single();

    @Resource
    private AdminCommons adminCommons;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();

        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("User access address: {}, IN address: {}", uri, IPKit.getIpAddrByRequest(request));


        //请求拦截处理
        SysAdmin login = TaleUtils.getLoginAdmin(request);

        //请求拦截处理
        String loginId = TaleUtils.getLoginUser(request);

        if (uri.contains("/index") && !uri.contains("/login") && null == login) {
            response.sendRedirect(request.getContextPath() + "/index/login");
            return false;
        }

        if (uri.contains("/user") && !uri.contains("/login") && !uri.contains("/user/user_login") && Strings.isBlank(loginId)) {
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/user/user_login");
            requestDispatcher.forward(request,response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("adminCommons", adminCommons);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
