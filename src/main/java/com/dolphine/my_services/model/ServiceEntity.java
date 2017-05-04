package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * Created by PC on 4/3/2017.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"catalog", "providerServices"})
@Table(name = "service")
public class ServiceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private CatalogEntity catalog;

    @OneToMany(mappedBy = "service")
    private List<ProviderServiceEntity> providerServices;

}
