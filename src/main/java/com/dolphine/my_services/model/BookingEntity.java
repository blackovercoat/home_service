package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/3/2017.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"customer", "staffs","providerServices"})
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "working_date")
    private Date workingDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "provider_service_id", nullable = false)
    private ProviderServiceEntity providerServices;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_detail",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id"))
    private List<StaffEntity> staffs;
}
