package ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.domain.Cust;
import ssm.domain.User;
import ssm.service.CustService;
import ssm.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cust")
public class CustController {
    @Autowired
    CustService custSrevice;
    @Autowired
    UserService userService;

    @RequestMapping("fandall")
    @ResponseBody
    public List<Cust> custFandall(String custName, String custType, HttpSession session) {
        if (custName==null){
            custName="";
        }
        if (custType==null){
            custType="";
        }
        List<Cust> list = custSrevice.custFandall(custName,custType);
        List<User> userList = userService.findAll();
        session.setAttribute("userList",userList);
        return list;
    }
    @RequestMapping("delete")
    public String dCust(Integer cid){
        Integer i = custSrevice.delCust(cid);
      return "redirect:/jsp/center.jsp";
    }
    @RequestMapping("/add")
    public String add(Cust customer) {
        custSrevice.add(customer);
        return "redirect:/jsp/center.jsp";
    }
}
