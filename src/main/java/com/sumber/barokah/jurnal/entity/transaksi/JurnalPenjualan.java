package com.sumber.barokah.jurnal.entity.transaksi;

import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.master.Product;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jurnal_penjualan")
@EntityListeners({AuditingEntityListener.class})
public class JurnalPenjualan {

    @Id
    @Column(name = "jurnal_penjualan_id")
    private String jurnalPenjualanId;

    @Column(name = "no_faktur")
    private String noFaktur;

    @Column(name = "tanggal_transaksi")
    private LocalDateTime tanggalTransaksi;

    @Column(name = "tanggal_jatuh_tempo")
    private LocalDateTime tanggalJatuhTempo;

    @Column(name = "status")
    private String status;

    @Column(name = "sisa_tagihan")
    private Long sisaTagihan;

    @Column(name = "jumlah_total")
    private Long jumlahTotal;

    @Column(name = "no_transaksi")
    private String noTransaksi;

    @Column(name = "tags")
    private String tags;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_modified_at")
    private Instant updateModifiedAt;

    // relation customers
    @ManyToOne
    @JoinColumn(name = "customers_id", referencedColumnName = "customers_id")
    private Customer customer;

    // relation product
    @OneToMany(mappedBy = "jurnalPenjualan")
    private List<Product> products;
}
