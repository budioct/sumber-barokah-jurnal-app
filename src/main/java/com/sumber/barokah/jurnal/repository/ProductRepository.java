package com.sumber.barokah.jurnal.repository;

import com.sumber.barokah.jurnal.entity.master.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
