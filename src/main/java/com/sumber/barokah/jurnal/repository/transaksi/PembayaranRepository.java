package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PembayaranRepository extends JpaRepository<Pembayaran, String>, JpaSpecificationExecutor<Pembayaran> {

    Optional<Pembayaran> findFirstByPembayaranId(String id);

}
