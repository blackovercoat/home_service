package com.dolphine.my_services.controller;

import com.dolphine.my_services.service.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by PC on 5/5/2017.
 */
@Controller
@RequestMapping("/")
public class ChartController {
    @Autowired
    final private ProviderService providerService;

    public ChartController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @RequestMapping("statistic/chart")
    public String chart(Model model){
        model.addAttribute("providerList",providerService.getAllProvider());
        return "statistic/chart";
    }
}
