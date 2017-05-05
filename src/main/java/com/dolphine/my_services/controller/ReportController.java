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

@Controller
public class ReportController {

    @Autowired
    final private ProviderService providerService;
    final private BookingService bookingService;

    public ReportController(ProviderService providerService, BookingService bookingService) {
        this.providerService = providerService;
        this.bookingService = bookingService;
    }
    @RequestMapping("statistic/reportRender")
    public String cha(){
        return "statistic/reportRender";
    }

    @RequestMapping("statistic/report")
    public String chart(Model model){
        float totalMin=0;
        float totalMax=0;
        String total;
        model.addAttribute("providerList",providerService.getAllProvider());
        model.addAttribute("defaultStatistic",bookingService.getServiceStatisticByProviderId(1,0));
        List<ServiceStatistic> serviceStatistics = bookingService.getServiceStatisticByProviderId(1,0);
        for(ServiceStatistic statistic : serviceStatistics){
            totalMin = totalMin + statistic.getMinPrice()*statistic.getBookingTimes();
            totalMax = totalMax + statistic.getMaxPrice()*statistic.getBookingTimes();
        }
        total = totalMin +" $ - " +totalMax +" $";
        model.addAttribute("defaultTotal",total);
        return "statistic/report";
    }

    @ResponseBody
    @RequestMapping(value = "statistic/load_report", method = RequestMethod.GET)
    public List<ServiceStatistic> loadSReport(@RequestParam(name = "providerId") int providerId
            ,@RequestParam(name = "month") int month) {
        List<ServiceStatistic> serviceStatistics = bookingService.getServiceStatisticByProviderId(providerId,month);
        return serviceStatistics;
    }
}
