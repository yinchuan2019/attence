package com.my.attence.config;


import com.my.attence.entity.BaseEntity;
import com.my.attence.entity.SysAdmin;
import com.my.attence.service.AdminService;
import com.my.attence.utils.AdminCommons;
import com.my.attence.utils.IPKit;
import com.my.attence.utils.MapCache;
import com.my.attence.utils.TaleUtils;
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
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));


        //请求拦截处理
        SysAdmin login = TaleUtils.getLoginAdmin(request);

        //请求拦截处理
        BaseEntity baseUser = TaleUtils.getLoginUser(request);

        if (uri.contains("/index") && !uri.contains("/login") && null == login) {
            response.sendRedirect(request.getContextPath() + "/index/login");
            return false;
        }

        if (uri.contains("/user") && !uri.contains("/1280.html") && null == baseUser) {
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/user/1280.html");
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
