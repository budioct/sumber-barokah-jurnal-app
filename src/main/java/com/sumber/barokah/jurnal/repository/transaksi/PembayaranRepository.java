package com.sumber.barokah.jurnal.repository.transaksi;

import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PembayaranRepository extends JpaRepository<Pembayaran, String> {
}
