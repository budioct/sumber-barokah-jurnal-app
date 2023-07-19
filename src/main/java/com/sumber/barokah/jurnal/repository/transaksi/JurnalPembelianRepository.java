package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JurnalPembelianRepository extends JpaRepository<JurnalPembelian, String> {

    //Optional<JurnalPembelian> findFirstBySupplierAndProductsAndjAndJurnalPembelianId(Supplier supplier, List<Product> product, String jurnalPembelianId);


}
