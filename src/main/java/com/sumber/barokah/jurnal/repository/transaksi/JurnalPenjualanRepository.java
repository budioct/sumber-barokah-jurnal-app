package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JurnalPenjualanRepository extends JpaRepository<JurnalPenjualan, String>, JpaSpecificationExecutor<JurnalPenjualan> {

    Optional<JurnalPenjualan> findFirstByJurnalPenjualanId(String id);

    Optional<JurnalPenjualan> findFirstByCustomerAndJurnalPenjualanId(Customer customer, String id);

    // delete from table a ,table_pivot (delete id from table a and table b), not delete reference table b where table.a.id = ''
    //@Query(value = "delete from jurnal_pembelian where jurnal_pembelian_id in (select jurnal_pembelian_id from jurnal_pembelian_like_product where jurnal_pembelian.jurnal_pembelian_id =:id)", nativeQuery = true)
    @Modifying
    @Query(value = "delete from jurnal_penjualan where jurnal_penjualan_id in (select jurnal_penjualan_id from jurnal_penjualan_like_product where jurnal_penjualan.jurnal_penjualan_id =:id)", nativeQuery = true)
    void deleteJurnalPenjualan(@Param("id") String id);

}
