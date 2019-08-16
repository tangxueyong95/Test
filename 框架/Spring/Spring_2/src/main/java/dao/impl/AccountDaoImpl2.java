package dao.impl;


import dao.AccountDao;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AccountDaoImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/11 8:48
 * @Version V1.0
 */

/**
 * 1：学会组件（注解IOC）
 @Component放置到类上（泛指组件）
 @Controller放置到类上（衍生组件，放置到类web层）
 @Service放置到类上（衍生组件，放置到类service层）
 @Repository放置到类上（衍生组件，放置到类dao层）
 */

/**
 * @Component：相当于spring容器中定义：
 * <bean id="accountDaoImpl" class="com.itheima.dao.impl.AccountDaoImpl"></bean>
 *   此时id就是类的名称，且首字母小写
 * @Component("accountDao")：相当于spring容器中定义：
 * <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl"></bean>
 */
@Repository("accountDao2")
public class AccountDaoImpl2 implements AccountDao {
    public void save() {
        System.out.println("执行AccountDaoImpl2中的save方法！");
    }
}
