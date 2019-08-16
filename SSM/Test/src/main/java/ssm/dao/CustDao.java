package ssm.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import ssm.domain.Cust;

import java.util.List;

public interface CustDao {
    List<Cust> Fandall(@Param("custName") String custName, @Param("custType") String custType);

    //    @Delete("delete FROM `s_cust` WHERE cid =#{cid}")
    Integer deleteCust(Integer cid);

    @Insert("INSERT INTO `ssm_exam`.`s_cust` (`cust_name`, `cust_type`, `cust_phone`, `cust_address`, `cust_link_user`)" +
            "        VALUES (#{custName}, #{custType}, #{custPhone}, #{custAddress}, #{user.uid});")
    void add(Cust customer);
}
