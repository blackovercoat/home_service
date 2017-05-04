package com.dolphine.my_services.service.catalog;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.CatalogEntity;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.model.RatingEntity;
import com.dolphine.my_services.model.ServiceEntity;
import com.dolphine.my_services.repository.CatalogRepository;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private final CatalogRepository catalogRepository;
    private final ProviderServiceRepository providerServiceRepository;

    public CatalogServiceImpl(CatalogRepository catalogRepository, ProviderServiceRepository providerServiceRepository) {
        this.catalogRepository = catalogRepository;
        this.providerServiceRepository = providerServiceRepository;
    }

    @Override
    public List<CatalogEntity> getAllCatalog() {
        return catalogRepository.findAll();
    }

    @Transactional
    @Override
    public List<CatalogAndService> getAllCatalogAndService() {
        List<CatalogEntity> catalogEntities = catalogRepository.findAll();
        List<CatalogAndService> catalogAndServiceList = convertListCatalogEntityToDTO(catalogEntities);
        return catalogAndServiceList;
    }

    @Override
    @Transactional
    public void removeCatalogById(int catalogId) {
        catalogRepository.delete(catalogId);
    }

    @Override
    public CatalogEntity addCatalog(CatalogForm catalogForm) {
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setName(catalogForm.getName());
        catalogEntity.setDescription(catalogForm.getDescription());
        catalogEntity.setImage(catalogForm.getImage());
        return catalogRepository.save(catalogEntity);
    }

    @Transactional
    @Override
    public List<CatalogServiceAndRating> getCatalogServiceAndRatingByProviderId(int providerId) {
        List<ProviderServiceEntity> providerServiceEntities = providerServiceRepository.findByProvider_Id(providerId);
        List<CatalogEntity> allCatalog = catalogRepository.findAll();
        List<ServiceAndRating> serviceAndRatings = new ArrayList<>();
        List<CatalogServiceAndRating> catalogServiceAndRatingList = new ArrayList<>();
        for(ProviderServiceEntity providerServiceEntity : providerServiceEntities) {
            List<RatingEntity> ratingEntities = providerServiceEntity.getRatings();
            List<Rating> ratings = new ArrayList<Rating>();
            for(RatingEntity ratingEntity : ratingEntities)
                ratings.add(new Rating(ratingEntity.getId()
                        ,ratingEntity.getCustomer().getId()
                        ,ratingEntity.getContent()
                        ,ratingEntity.getScore()
                        ,ratingEntity.getTitle()
                        ,ratingEntity.getRatingDate()
                        ,ratingEntity.getProviderServices().getId()));
            serviceAndRatings.add(new ServiceAndRating(providerServiceEntity.getService().getId()
                    , providerServiceEntity.getService().getName()
                    , providerServiceEntity.getService().getDescription()
                    , providerServiceEntity.getService().getImage()
                    , ratings));
        }
        for(CatalogEntity catalogEntity : allCatalog){
            List<ServiceAndRating> services = new ArrayList<>();
            for(ServiceAndRating serviceAndRating : serviceAndRatings)
                if(serviceAndRating.getId()==catalogEntity.getId())
                    services.add(new ServiceAndRating(serviceAndRating.getId()
                            ,serviceAndRating.getName()
                            ,serviceAndRating.getDescription()
                            ,serviceAndRating.getImage()
                            ,serviceAndRating.getRatings()));

            if(!services.isEmpty())
                catalogServiceAndRatingList.add(new CatalogServiceAndRating(catalogEntity.getId()
                        ,catalogEntity.getName()
                        ,catalogEntity.getDescription()
                        ,services
                        ,catalogEntity.getImage()));
        }
        return catalogServiceAndRatingList;
    }

    @Transactional
    @Override
    public CatalogEntity getCatalogById(int catalogId) {
        return catalogRepository.findOne(catalogId);
    }

    @Override
    public void setCatalogById(CatalogForm catalogForm, int id) {
        catalogRepository.updateById(id,catalogForm.getName()
                ,catalogForm.getDescription()
                ,catalogForm.getImage());
    }

    private List<CatalogAndService> convertListCatalogEntityToDTO(List<CatalogEntity> catalogEntities){
        List<CatalogAndService> catalogAndServiceList = new ArrayList<>();
        for(CatalogEntity catalogEntity : catalogEntities){
            List<ServiceEntity> serviceEntities = catalogEntity.getServices();
            List<ServiceWebService> services = new ArrayList<ServiceWebService>();
            for(ServiceEntity serviceEntity : serviceEntities)
                services.add(new ServiceWebService(serviceEntity.getId(),serviceEntity.getName(),serviceEntity.getDescription(),serviceEntity.getImage()));
            catalogAndServiceList.add(new CatalogAndService(catalogEntity.getId()
                    ,catalogEntity.getName(),catalogEntity.getDescription(),services,catalogEntity.getImage()));
        }
        return catalogAndServiceList;
    }
}
