package com.sumber.barokah.jurnal.entity.transaksi;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
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

    @Column(name = "total_bayar")
    private Long totalPembayaran;

    @Column(name = "status")
    private String status;

    @Column(name = "keterangan")
    private String keterangan;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

    //@JsonIgnore
    //@ManyToOne
    //@JoinColumn(name = "jurnal_pembelian_id", referencedColumnName = "jurnal_pembelian_id")
    //private JurnalPembelian jurnalPembeliansLikeBy;

    //@JsonIgnore
    //@ManyToMany(mappedBy = "like_pembayaran0", cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "jurnal_pembelian_like_pembayaran",
            joinColumns = @JoinColumn(name = "pembayaran_id", referencedColumnName = "pembayaran_id"),
            inverseJoinColumns = @JoinColumn(name = "jurnal_pembelian_id", referencedColumnName = "jurnal_pembelian_id")
    )
    private List<JurnalPembelian> like_jurnal_pembelian;

    //@ManyToMany(mappedBy = "likes1")
    //private List<JurnalPenjualan> jurnalPenjualansLikeBy;

}
