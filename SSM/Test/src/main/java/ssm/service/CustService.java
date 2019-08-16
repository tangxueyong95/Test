package ssm.service;

import ssm.domain.Cust;

import java.util.List;

public interface CustService {
    List<Cust> custFandall(String custName,String custType);

    Integer delCust(Integer cid);
    void add(Cust customer);
}
