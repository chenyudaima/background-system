//package com.chenyudaima.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.FileNotFoundException;
//
///**
// * @author 沉鱼代码
// * @date 2022/12/21
// */
//@Aspect
//@Component
//public class SecurityAspect {
//    // 定义一个切入点
//    @Pointcut("execution(* com.chenyudaima.web.controller.*.*(..))")
//    public void aspect() {}
//
//
//    // 前置通知
//    @Before(value = "aspect()")
//    public void before(JoinPoint jp) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        System.out.println("切面成功");
//        throw new ArithmeticException("抛出异常");
//    }
//
//}
