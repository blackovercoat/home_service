package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by PC on 3/30/2017.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"provider","bookings"})
@Table(name = "staff")
public class StaffEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity provider;

    @ManyToMany(mappedBy = "staffs")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "bookingDetailEntityPK.staffById", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookingDetailEntity> bookingDetails;
}
