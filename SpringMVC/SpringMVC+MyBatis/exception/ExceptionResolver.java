package com.springMVC.demo1.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        //记录日志
        e.printStackTrace();
        //异常消息
        String result="系统异常";
        //异常判断，是否为自定义异常
        if (e instanceof  MyException)
            result=e.getMessage();
        //将异常信息响应给用户
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message",result);
        modelAndView.setViewName("exception");
        return modelAndView;
    }
}
