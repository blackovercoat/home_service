package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/3/2017.
 */
@Data
@Entity
@ToString
@EqualsAndHashCode(exclude = {"provider", "service","ratings","bookings"})
@Table(name = "provider_service")
public class ProviderServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "max_price")
    private float maxPrice;

    @Column(name = "min_price")
    private float minPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "from_time")
    private Date fromTime;

    @Column(name = "to_time")
    private Date toTime;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity provider;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @OneToMany(mappedBy = "providerServices")
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "providerServices")
    private List<BookingEntity> bookings;
}
