package com.funnysec.richardtang.funnytools.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.funnysec.richardtang.funnytools.entity.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局属性-拦截器
 * 当希望一个对象属性能在所有页面或者多个页面中使用时可以添加到该拦截器中
 *
 * @author RichardTang
 * @date 2020/4/7
 */
public class GlobalAttrInterceptor implements HandlerInterceptor {

    @Autowired
    private System system;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("system", system);
    }
}
