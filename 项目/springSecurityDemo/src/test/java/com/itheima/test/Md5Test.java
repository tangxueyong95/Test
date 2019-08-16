package com.itheima.test;

import com.itheima.health.utils.MD5Utils;
import org.junit.Test;

/**
 * @ClassName Md5Test
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/5 17:49
 * @Version V1.0
 */
public class Md5Test {
    @Test
    public void md5(){
        String s1 = MD5Utils.md5("zhangsan123zhangsan123");
        String s2 = MD5Utils.md5("zhangsan123zhangsan123");
        System.out.println(s1);
        System.out.println(s2);
    }
}
