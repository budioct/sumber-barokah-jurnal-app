package com.sumber.barokah.jurnal.entity.transaksi;

import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
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
@Table(name = "jurnal_pembelian")
@EntityListeners({AuditingEntityListener.class})
public class JurnalPembelian {

    @Id
    @Column(name = "jurnal_pembelian_id")
    private String jurnalPembelianId;

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

    // relation suppliers
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    private Supplier supplier;

    // relation product
    @OneToMany(mappedBy = "jurnalPembelian")
    private List<Product> products;

    @ManyToMany
    @JoinTable(
            name = "jurnal_pembelian_like_pembayaran",
            joinColumns = @JoinColumn(name = "jurnal_pembelian_id", referencedColumnName = "jurnal_pembelian_id"),
            inverseJoinColumns = @JoinColumn(name = "pembayaran_id", referencedColumnName = "pembayaran_id")
    )
    private List<Pembayaran> likes0;

}
