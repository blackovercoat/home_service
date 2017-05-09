package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Data
@ToString
public class CatalogServiceAndRating {
        private int id;
        private String name;
        private String description;
        private List<ProviderServiceWebService> providerServide;
        private String image;

        public CatalogServiceAndRating(int id, String name, String description, List<ProviderServiceWebService> providerServide, String image) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.providerServide = providerServide;
                this.image = image;
        }
}
