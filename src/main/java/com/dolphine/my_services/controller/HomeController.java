package com.dolphine.my_services.controller;

import com.dolphine.my_services.model.AccountEntity;
import com.dolphine.my_services.model.RoleEntity;
import com.dolphine.my_services.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by PC on 3/10/2017.
 */
@Controller
public class HomeController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/")

    String index(Principal principal, ModelMap model) {
        String email = principal.getName();
        AccountEntity acc = this.accountService.getAccountByEmail(email);
        model.addAttribute("acc", acc);
        for (RoleEntity role : acc.getRoles()) {
            String userRole = role.getName();
            if ("ROLE_ADMIN".equals(userRole)) {
                return "redirect:provider/list";
            } else if ("ROLE_USER".equals(userRole)) {
                return "redirect:manage/employee";
            }
        }
        return "users/403";
    }
}
