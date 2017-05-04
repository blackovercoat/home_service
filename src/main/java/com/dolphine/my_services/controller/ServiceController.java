package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.ServiceForm;
import com.dolphine.my_services.dto.ServiceDTO;
import com.dolphine.my_services.model.ServiceEntity;
import com.dolphine.my_services.service.catalog.CatalogService;
import com.dolphine.my_services.service.common.CommonService;
import com.dolphine.my_services.service.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    final private CatalogService catalogService;
    final private ServicesService servicesService;
    final private CommonService commonService;

    public ServiceController(CatalogService catalogService, ServicesService servicesService, CommonService commonService) {
        this.catalogService = catalogService;
        this.servicesService = servicesService;
        this.commonService = commonService;
    }

    @RequestMapping("/list")
    public String services(Model model){
        model.addAttribute("listCatalog",catalogService.getAllCatalog());
        model.addAttribute("defaultservice",servicesService.getServicesByCatalogId(1));
        return "service/service_list";
    }

    @ResponseBody
    @RequestMapping(value = "/service_list", method = RequestMethod.GET)
    public List<ServiceDTO> loadService(@RequestParam(name = "catalogId") int catalogId) {
        List<ServiceDTO> servicesList = servicesService.getServicesByCatalogId(catalogId);
        return servicesList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewService(@RequestParam("fileUpload") MultipartFile fileUpload, @Valid @ModelAttribute("serviceForm") ServiceForm serviceForm, BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "service/service_list";
        ServiceEntity serviceEntity = servicesService.addService(serviceForm);
        try {
            serviceForm.setImage(commonService.uploadImage(fileUpload,"catalog"+serviceEntity.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        servicesService.setServiceById(serviceForm,serviceEntity.getId());
        redirectAttributes.addFlashAttribute("addServiceSuccessMessage", "Add new service successful!");
        return "redirect:/service/list";
    }

    @RequestMapping(value = "/edit/{serviceId}",method = RequestMethod.GET)
    public String editCatalog(@PathVariable("serviceId") int serviceId,Model model){
        ServiceEntity serviceEntity = servicesService.getServiceById(serviceId);
        model.addAttribute("serviceForm", new ServiceForm());
        model.addAttribute("service",serviceEntity);
        return "service/edit_service";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateCatalog(@RequestParam("fileUpload") MultipartFile fileUpload,@ModelAttribute("serviceForm") ServiceForm serviceForm
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/service/edit/"+serviceForm.getId();
        }
        if(fileUpload.getName()!="")
            try {
                commonService.removeImage(serviceForm.getImage());
                serviceForm.setImage(commonService.uploadImage(fileUpload,"service"+serviceForm.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        servicesService.setServiceById(serviceForm,serviceForm.getId());
        return "redirect:/service/list";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProvider(@RequestBody ServiceDTO service) {
        int id = service.getId();
        servicesService.removeServiceById(id);
        return "redirect:/service/list";
    }

}
