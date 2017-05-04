package com.dolphine.my_services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by PC on 3/11/2017.
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login")
    String login() {
        return "users/login";
    }

    @RequestMapping(value = "/login-error")
    String loginError(ModelMap model) {
        model.addAttribute("error", "Login Error!");
        return "users/login";
    }

    @RequestMapping(value = "/logout")
    String logout(ModelMap model) {
        model.addAttribute("logout", "Logout Successful!");
        return "users/login";
    }

    @RequestMapping(value = "/403")
    String accessDenied() {
        return "users/403";
    }
}
