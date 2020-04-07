package com.funnysec.richardtang.funnytools.interceptor;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证-拦截器
 *
 * @author RichardTang
 * @date 2020年3月15日21:33:11
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        // 未登录
        if (ObjectUtil.isNull(user)) {
            response.sendRedirect("login");
            return false;
        } else {
            // 已登录
            return true;
        }
    }
}
