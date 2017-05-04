package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Data
@ToString
public class CatalogAndService {
        private int id;
        private String name;
        private String description;
        private List<ServiceWebService> servicesList;
        private String image;

        public CatalogAndService(int id, String name, String description, List<ServiceWebService> servicesList, String image) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.servicesList = servicesList;
                this.image = image;
        }
}
