package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.Catalog;
import com.dolphine.my_services.dto.CatalogForm;
import com.dolphine.my_services.model.CatalogEntity;
import com.dolphine.my_services.service.catalog.CatalogService;
import com.dolphine.my_services.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by PC on 4/11/2017.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    final private CatalogService catalogService;
    final private CommonService commonService;

    public CatalogController(CatalogService catalogService, CommonService commonService) {
        this.catalogService = catalogService;
        this.commonService = commonService;
    }

    @RequestMapping("/list")
    public String catalogs(Model model){
        model.addAttribute("catalogs",catalogService.getAllCatalog());
        return "catalog/catalog_list";
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
    public String addNewCatalog(@RequestParam("fileUpload") MultipartFile fileUpload, @ModelAttribute("catalogForm") CatalogForm catalogForm, BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "catalog/catalog_list";
        CatalogEntity catalogEntity = catalogService.addCatalog(catalogForm);
        try {
            catalogForm.setImage(commonService.uploadImage(fileUpload,"catalog"+catalogEntity.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catalogService.setCatalogById(catalogForm,catalogEntity.getId());
        redirectAttributes.addFlashAttribute("addCatalogSuccessMessage", "Add new catalog successful!");
        return "redirect:/catalog/list";
    }

    @RequestMapping(value = "/edit/{catalogId}",method = RequestMethod.GET)
    public String editCatalog(@PathVariable("catalogId") int catalogId,Model model){
        CatalogEntity catalogEntity = catalogService.getCatalogById(catalogId);
        model.addAttribute("catalogForm", new CatalogForm());
        model.addAttribute("catalog",catalogEntity);
        return "catalog/edit_catalog";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateCatalog(@RequestParam("fileUpload") MultipartFile fileUpload,@ModelAttribute("catalogForm") CatalogForm catalogForm
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/catalog/edit/"+catalogForm.getId();
        }
        if(fileUpload.getName()!="")
            try {
                commonService.removeImage(catalogForm.getImage());
                catalogForm.setImage(commonService.uploadImage(fileUpload,"catalog"+catalogForm.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        catalogService.setCatalogById(catalogForm,catalogForm.getId());
        return "redirect:/catalog/list";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProvider(@RequestBody Catalog catalog) {
        int id = catalog.getId();
        catalogService.removeCatalogById(id);
        return "redirect:/catalog/list";
    }
}
