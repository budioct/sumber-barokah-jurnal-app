package com.sumber.barokah.jurnal.repository.master;

import com.sumber.barokah.jurnal.entity.master.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

}
