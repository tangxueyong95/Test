package com.example.test.io;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.CtNewMethod;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //设置目标类的路径(即目标类的class路径，我这里的test2.class是在工程下WebRoot/WEB-INF/classes/com/hcj/javaassist)
        pool.insertClassPath("G:\\");
        //获得要修改的类（注意
        //   ，一定要类的全称）
        CtClass ctClass = pool.get("com.dh.ifcs.kafka.subscribe.TakeFromFaultQueen");
        try {
            CtMethod dealDeviceStatusSummary = ctClass.getDeclaredMethod("dealDeviceStatusSummary");
            CtMethod newMethod = CtNewMethod.copy(dealDeviceStatusSummary, ctClass, null);
            // 修改原方法
            newMethod.setBody("{}");
            //修改方法名
            String oldName = dealDeviceStatusSummary.getName() + "1";
            dealDeviceStatusSummary.setName(oldName);
            //修改方法体(自定义参数类必须放在相应包位置)
//            dealDeviceStatusSummary.setBody("{}");

//            String methodStr ="private void dealDeviceStatusSummary(String dataType, java.util.Map/*<String, Object>*/ mapItem, com.dh.ifcs.basicdata.entity.DeviceVo host) {"
//                    +"}";
//            CtMethod newMethod = CtNewMethod.make(methodStr, ctClass);
            // 将新方法添加到类中
            ctClass.addMethod(newMethod);
            ctClass.writeFile("G:\\tmp_code\\test\\dh\\");
            System.out.println(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
