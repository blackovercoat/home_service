package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.ServiceStatistic;
import com.dolphine.my_services.service.booking.BookingService;
import com.dolphine.my_services.service.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by PC on 5/5/2017.
 */
@Controller
@RequestMapping("/")
public class ChartController {
    @Autowired
    final private ProviderService providerService;
    final private BookingService bookingService;

    public ChartController(ProviderService providerService, BookingService bookingService) {
        this.providerService = providerService;
        this.bookingService = bookingService;
    }

    @RequestMapping("statistic/chart")
    public String chart(Model model){
        model.addAttribute("providerList",providerService.getAllProvider());
        return "statistic/chart";
    }

    @ResponseBody
    @RequestMapping(value = "statistic/service_statistic", method = RequestMethod.GET)
    public List<ServiceStatistic> loadStatistic(@RequestParam(name = "providerId") int providerId) {
        List<ServiceStatistic> serviceStatistics = bookingService.getServiceStatisticByProviderId(providerId,0);
        return serviceStatistics;
    }

    @ResponseBody
    @RequestMapping(value = "statistic/service_times", method = RequestMethod.GET)
    public List<ServiceStatistic> loadServiceTime(@RequestParam(name = "providerId") int providerId
            ,@RequestParam(name = "month") int month) {
        List<ServiceStatistic> serviceStatistics = bookingService.getServiceStatisticByProviderId(providerId,month);
        return serviceStatistics;
    }
}
