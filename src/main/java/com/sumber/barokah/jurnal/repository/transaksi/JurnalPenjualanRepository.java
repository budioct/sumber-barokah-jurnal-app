package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPenjualan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JurnalPenjualanRepository extends JpaRepository<JurnalPenjualan, String>, JpaSpecificationExecutor<JurnalPenjualan> {

    Optional<JurnalPenjualan> findFirstByJurnalPenjualanId(String id);

    Optional<JurnalPenjualan> findFirstByCustomerAndJurnalPenjualanId(Customer customer, String id);

}
