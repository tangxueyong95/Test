package ssm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ssm.domain.User;
import ssm.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    public String login(HttpSession session,String username, String password) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            session.setAttribute("user",user);
            return "redirect:/jsp/index.jsp";
        }
        return "redirect:/jsp/login.jsp";
    }

    // 获取
    @RequestMapping(value = "/sessionAttributeGet")
    @ResponseBody
    public String sessionAttributeGet(HttpSession session){
        User user =(User) session.getAttribute("user");
        return user.getUsername();
    }

    // 清空
    @RequestMapping(value = "/sessionAttributeClean")
    public String sessionAttributeClean(HttpSession session){
        session.invalidate();
        return "redirect:/jsp/login.jsp";
    }
}
