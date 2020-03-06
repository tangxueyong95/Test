package com.ssm.controller;


import com.ssm.domain.Account;
import com.ssm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    // 查询所有（视图解析器）
    @RequestMapping(value = "/findAll")
    public String findAll(Model model) {
        System.out.println("访问Controller类中的findAll方法！");
        List<Account> list = accountService.findAllAccount();
        for (Account account : list) {
            System.out.println(account);
        }
        model.addAttribute("list", list);
        return "success";
    }

    // 保存（视图解析器）
    @RequestMapping(value = "/save")
    public String save(Account account) {
        System.out.println("访问Controller类中的save方法！");
        int rows = accountService.saveAccount(account);
        System.out.println(rows);
        return "redirect:/account/findAll";
    }
}
