package com.java.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 12:16
 * @Description: com.java.timer
 * 集成定时任务
 * 1）开启定时任务,在启动类上加一个注解@EnableScheduling
 * 2）在需要定时实行的方法上加上一个注解@Scheduled
 *    这个方法所在的类必须被SpringIOC容器管理
 ****/
@Component
public class UserInfoTask {

    /****
     * 写定时执行的方法
     * fixedDelay:距离上一次执行完毕之后，隔N毫秒执行
     * fixedDelayString:使用配置文件中的占位符参数
     *
     * fixedRate = 2000:距离上次认识开始执行后N毫秒开始执行
     *                  如果上次任务没有按时执行完，则上次任务执行完之后立即执行
     * @Scheduled(fixedRateString ="")
     * @Scheduled(initialDelay = 10000,fixedDelay=2000):第一次执行，延时N毫秒
     */
    //@Scheduled(fixedDelay = 2000)
    //@Scheduled(fixedDelayString ="${time.delay}")    //使用配置文件中的占位符参数
    //@Scheduled(initialDelay = 10000,fixedDelay=2000)
    @Scheduled(cron = "5/15 * * * * ?") //5秒开始执行，每隔15秒再执行
    public void task(){
         System.out.println(LocalDateTime.now());
    }

}
