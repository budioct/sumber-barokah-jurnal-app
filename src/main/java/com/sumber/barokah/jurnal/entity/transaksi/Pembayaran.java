package com.sumber.barokah.jurnal.entity.transaksi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pembayaran")
@EntityListeners({AuditingEntityListener.class})
public class Pembayaran {

    @Id
    @Column(name = "pembayaran_id")
    private String pembayaranId;

    @Column(name = "tanggal_pembayaran")
    private LocalDateTime tanggalPembayaran;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

    //@ManyToMany(mappedBy = "likes0")
    //private List<JurnalPembelian> jurnalPembeliansLikeBy;

    //@ManyToMany(mappedBy = "likes1")
    //private List<JurnalPenjualan> jurnalPenjualansLikeBy;

}
