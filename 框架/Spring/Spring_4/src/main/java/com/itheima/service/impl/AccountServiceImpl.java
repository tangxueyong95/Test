package com.itheima.service.impl;

import com.itheima.dao.AccountDao;
import com.itheima.domain.Account;
import com.itheima.service.AccountService;

/**
 * @ClassName AccountServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/15 11:24
 * @Version V1.0
 */

/**
 * @Transactional：放置到类上，表示对类中的所有的方法都有效（全局）
 * @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)：放置到方法上
 *    1：说明方法级别的事务，覆盖类级别的事务；方法级别的事务的优先级更高
 *    2：@Transactional(readOnly = true)：尽量放置到类上，不要放置到接口上
 *        如果放置到接口上，此时产生事务的代理对象，一定是JDK代理，不能是CGLIB代理
 */
//@Transactional(readOnly = true)
//@Transactional
public class AccountServiceImpl implements AccountService{

    AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // 转账的案例（aaa取100元钱给bbb)
    // @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public void transfer(final String fromName, final String toName, final float money) {
        // 1：使用转账人的姓名，查询转账人账号信息，获取转账人的账号对象
        Account fromAccount = accountDao.findAccountByName(fromName);
        // 2：使用收账人的姓名，查询收账人账号信息，获取收账人的账号对象
        Account toAccount = accountDao.findAccountByName(toName);
        // 3：转账人的账号-money
        fromAccount.setMoney(fromAccount.getMoney()-money);
        // 4：收账人的账号+money
        toAccount.setMoney(toAccount.getMoney()+money);
        // 5：更新转账人的账号
        accountDao.updatAccount(fromAccount);
        // 抛出异常
        int i = 10/0;
        // 6：更新收账人的账号
        accountDao.updatAccount(toAccount);
    }

    public void saveAccount(Account account){

    };
    public void updateAccount(Account account){

    }
}
