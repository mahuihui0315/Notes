package com.springMVC.demo1.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    /**
     *Controller方法执行前被执行
     * 登陆拦截，权限验证
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandler执行");
        if (httpServletRequest.getSession().getAttribute("username")==null)
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/user/loginPage");
        /**
         * true：放行
         * false：拦截
         */
        return true;
    }

    /**
     *Controller方法执行后，返回ModelAndView之前被执行
     * 设置或者清理页面公用参数
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler执行");
    }

    /**
     *Controller方法执行后被执行
     * 处理异常，记录日志
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterHandler执行");
    }
}
