package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ssm.dao.CustDao;
import ssm.domain.Cust;
import ssm.service.CustService;

import java.util.List;
@Transactional
public class CustSreviceImpl implements CustService {
    @Autowired
    CustDao custDao;
    @Override
    public List<Cust> custFandall(String custName,String custType) {
       List<Cust> list = custDao.Fandall(custName,custType);
        return list;
    }

    @Override
    public Integer delCust(Integer cid) {
        Integer i = custDao.deleteCust(cid);
        return i;
    }
    @Override
    public void add(Cust customer) {
        custDao.add(customer);
    }
}
