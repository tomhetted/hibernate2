package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "store", schema = "hibernate2")
@Getter
@Setter
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Byte id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private StaffEntity staff;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
