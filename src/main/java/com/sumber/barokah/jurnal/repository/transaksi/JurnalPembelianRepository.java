package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JurnalPembelianRepository extends JpaRepository<JurnalPembelian, String>, JpaSpecificationExecutor<JurnalPembelian> {

    //Optional<JurnalPembelian> findFirstBySupplierAndProductsAndjAndJurnalPembelianId(Supplier supplier, List<Product> product, String jurnalPembelianId);

    Optional<JurnalPembelian> findFirstByJurnalPembelianId(String id);

    Optional<JurnalPembelian> findFirstBySupplierAndJurnalPembelianId(Supplier supplier, String id);

    // delete from table a ,table_pivot (delete id from table a and table b), not delete reference table b where table.a.id = ''
    // delete  from jurnal_pembelian where jurnal_pembelian_id in (select jurnal_pembelian_id from jurnal_pembelian_like_product where jurnal_pembelian.jurnal_pembelian_id = '9c35e3cf-cb5e-46e2-b8f2-35bbc07ef1f5');
//    @Query(value = "DELETE FROM JurnalPembelian jp WHERE jp.jurnalPembelianId in (SELECT jp FROM jurnal_pembelian_like_product WHERE jp.jurnalPembelianId =: id)")
    @Modifying
    @Query(value = "delete from jurnal_pembelian where jurnal_pembelian_id in (select jurnal_pembelian_id from jurnal_pembelian_like_product where jurnal_pembelian.jurnal_pembelian_id =:id)", nativeQuery = true)
    void deleteJurnalPembelian(@Param("id") String id);

}
