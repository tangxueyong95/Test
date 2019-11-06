package dao.impl;


import dao.AccountDao;
import org.springframework.stereotype.Repository;

/**
 @Component放置到类上（泛指组件）
 @Controller放置到类上（衍生组件，放置到类web层）
 @Service放置到类上（衍生组件，放置到类service层）
 @Repository放置到类上（衍生组件，放置到类dao层）
 */

/**
 * @Component：相当于spring容器中定义：
 * <bean id="accountDaoImpl" class="dao.impl.AccountDaoImpl"></bean>
 *   此时id就是类的名称，且首字母小写
 * @Component("accountDao")：相当于spring容器中定义：
 * <bean id="accountDao" class="dao.impl.AccountDaoImpl"></bean>
 */
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    public void save() {
        System.out.println("执行AccountDaoImpl中的save方法！");
    }
}
