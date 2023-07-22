package com.sumber.barokah.jurnal.repository.master;

import com.sumber.barokah.jurnal.entity.master.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, JpaSpecificationExecutor<Supplier> {

    // query method
    Optional<Supplier> findFirstBySupplierId(String id);

    Page<Supplier> findAllByOrderByCreateAtAsc(Pageable pageable);

}
