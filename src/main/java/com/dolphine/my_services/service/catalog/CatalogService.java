package com.dolphine.my_services.service.catalog;

import com.dolphine.my_services.dto.Catalog;
import com.dolphine.my_services.dto.CatalogAndService;
import com.dolphine.my_services.dto.CatalogForm;
import com.dolphine.my_services.dto.CatalogServiceAndRating;
import com.dolphine.my_services.model.CatalogEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
public interface CatalogService {

    List<CatalogEntity> getAllCatalog();
    List<CatalogAndService> getAllCatalogAndService();
    void removeCatalogById(int catalogId);
    CatalogEntity addCatalog(CatalogForm catalogForm);
    List<CatalogServiceAndRating> getCatalogServiceAndRatingByProviderId(int providerId);
    CatalogEntity getCatalogById(int catalogId);
    void setCatalogById(CatalogForm catalogForm,int id);
}
