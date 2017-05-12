package com.dolphine.my_services.controller;

/**
 * Created by PC on 3/15/2017.
 */

import com.dolphine.my_services.dto.Provider;
import com.dolphine.my_services.dto.ProviderForm;
import com.dolphine.my_services.model.ProviderEntity;
import com.dolphine.my_services.service.common.CommonService;
import com.dolphine.my_services.service.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private final ProviderService providerService;
    private final CommonService commonService;

    public ProviderController(ProviderService providerService, CommonService commonService) {
        this.providerService = providerService;
        this.commonService = commonService;
    }

    @RequestMapping("/add")
    public String newProvider(){
        return "provider/add_provider";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewProvider(@RequestParam("fileUpload") MultipartFile fileUpload,@Valid @ModelAttribute("providerForm") ProviderForm providerForm, BindingResult bindingResult
                              , RedirectAttributes redirectAttributes,Model model) throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()){
            if(providerService.getProviderByEmail(providerForm.getEmail())!=null)
                redirectAttributes.addFlashAttribute("emailErrorMessage","This email already in use!");
            else if(providerService.getProviderByPhoneNumber(providerForm.getPhoneNumber())!=null)
                redirectAttributes.addFlashAttribute("phoneErrorMessage","This phone number already in use!");
            else if(!providerForm.getPassword().equals(providerForm.getRepassword()))
                redirectAttributes.addFlashAttribute("passwordErrorMessage","Password and re-password not match!");
            return "redirect:/provider/add";
        }
        ProviderEntity providerEntity = providerService.addProvider(providerForm);
        try {
            providerForm.setImage(commonService.uploadImage(fileUpload,"provider"+providerEntity.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        providerService.setProviderById(providerForm,providerEntity.getId());
        redirectAttributes.addFlashAttribute("addProviderSuccessMessage", "Add new provider successful!");

        return "redirect:/provider/list";
    }

    @RequestMapping("/list")
    public String providers(Model model){
        model.addAttribute("listProvider",providerService.getAllProvider());
        return "provider/provider_list";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProvider(@RequestBody Provider provider) {
        int id = provider.getId();
        providerService.removeProviderById(id);
        return "redirect:/provider/list";
    }

    @RequestMapping(value = "/edit/{providerId}",method = RequestMethod.GET)
    public String editProvider(@PathVariable("providerId") int providerId,Model model){
        ProviderEntity providerEntity = providerService.getProviderById(providerId);
        model.addAttribute("providerForm", new ProviderForm());
        model.addAttribute("provider",providerEntity);
        return "provider/edit_provider";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateProvider(@RequestParam("fileUpload") MultipartFile fileUpload,@Valid @ModelAttribute("providerForm") ProviderForm providerForm
            , BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            if(providerService.getProviderByEmail(providerForm.getEmail())!=null&&
                    providerService.getProviderById(providerForm.getId()).getEmail()!=providerForm.getEmail())
                redirectAttributes.addFlashAttribute("emailErrorMessage","This email already in use!");
            else if(providerService.getProviderByPhoneNumber(providerForm.getPhoneNumber())!=null&&
                    providerService.getProviderById(providerForm.getId()).getPhoneNumber()!=providerForm.getPhoneNumber())
                redirectAttributes.addFlashAttribute("phoneErrorMessage","This phone number already in use!");
            else if(!providerForm.getPassword().equals(providerForm.getRepassword()))
                redirectAttributes.addFlashAttribute("passwordErrorMessage","Password and re-password not match!");
            return "redirect:/provider/edit/"+providerForm.getId();
        }
        if(fileUpload.getName()!="")
            try {
                commonService.removeImage(providerForm.getImage());
                providerForm.setImage(commonService.uploadImage(fileUpload,"provider"+providerForm.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        providerService.setProviderById(providerForm,providerForm.getId());
        return "redirect:/provider/list";
    }
}
