package com.sumber.barokah.jurnal.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@EntityListeners({AuditingEntityListener.class}) // supaya bisa aktif create_data dan last_modified_data saat query
public class Customer {

    @Id
    @Column(name = "customer_id")
    private String customerId;

    private String name;

    private String company;

    private Long saldo;

    @Column(name = "no_handphone")
    private String noHPhone;

    private String email;

    private String address;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

}
