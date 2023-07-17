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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jurnal_penjualan")
@EntityListeners({AuditingEntityListener.class})
public class JurnalPenjualan {

    /**
     * jurnal_penjualan_id,varchar(100),NO,PRI,,""
     * no_faktur,varchar(100),YES,"",,""
     * tanggal_transaksi,timestamp,YES,"",,""
     * tanggal_jatuh_tempo,timestamp,YES,"",,""
     * status,varchar(100),YES,"",,""
     * sisa_tagihan,bigint,YES,"",,""
     * jumlah_total,bigint,YES,"",,""
     * no_transaksi,varchar(100),YES,"",,""
     * tags,varchar(100),YES,"",,""
     * customers_id,varchar(100),NO,MUL,,""
     * product_id,varchar(100),NO,MUL,,""
     * create_at,timestamp,YES,"",,""
     * update_modified_at,timestamp,YES,"",,""
     */

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
    // relation product

}
