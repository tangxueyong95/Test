package com.itheima.test;

import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.junit.Test;

/**
 * @ClassName TestPOI
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/29 14:49
 * @Version V1.0
 */
public class TestSMS {


    @Test
    public void importData() throws Exception {
        SMSUtils.sendShortMessage("13058549564", ValidateCodeUtils.generateValidateCode(4).toString());
    }

}
