package com.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

// 切面
public class Logger {

    //通知（前置通知：在访问目标对象方法之前，先执行通知）
    public void beforeWriteLog(JoinPoint jp){
        System.out.println("【前置通知】：执行日志...，访问方法的名称是："+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
    }

    //通知（后置通知：在访问目标对象方法之后，再执行通知，如果目标对象抛出异常，将不会执行后置通知）
    public void afterReturningWriteLog(JoinPoint jp){
        System.out.println("【后置通知】：执行日志...，访问方法的名称是："+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
    }

    //通知（异常通知：在访问目标对象方法之后，如果目标对象方法抛出异常，就会执行异常通知）
    public void afterThrowingWriteLog(JoinPoint jp){
        System.out.println("【异常通知】：执行日志...，访问方法的名称是："+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
    }

    //通知（最终通知：在访问目标对象方法之后，不管是否抛出异常，都必须执行最终通知）
    public void afterWriteLog(JoinPoint jp){
        System.out.println("【最终通知】：执行日志...，访问方法的名称是："+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
    }

    //通知（环绕通知：需要手动开启调用目标对象的方法，既然可以手动，可以在环绕通知中实现，前置，后置，异常，最终）
    // 如果是环绕通知，需要使用JoinPoint的一个子类，ProceedingJoinPoint，通过它可以调用目标对象的方法
    // 如果是环绕通知，此时不能使用void，使用一个Object作为方法返回值
    public Object aroundWriteLog(ProceedingJoinPoint jp){
        System.out.println("【环绕通知】：执行日志...，访问方法的名称是："+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName());
        // 思想和jdk代理一样
        Object returnValue = null;
        try {
            this.beforeWriteLog(jp); // 前置通知
            returnValue = jp.proceed(jp.getArgs()); // 手动调用了目标对象
            this.afterReturningWriteLog(jp); // 后置通知
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            this.afterThrowingWriteLog(jp); // 异常通知
        } finally {
            this.afterWriteLog(jp); //最终通知
        }
        return returnValue;
    }


}
