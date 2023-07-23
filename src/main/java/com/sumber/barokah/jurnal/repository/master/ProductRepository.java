package com.sumber.barokah.jurnal.repository.master;

import com.sumber.barokah.jurnal.entity.master.Category;
import com.sumber.barokah.jurnal.entity.master.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

    // query method
    Optional<Product> findFirstByCategoryAndProductId(Category category, String productId);

    //<T> Optional<List<T>> findFirstByProductId(List<T> productId);
    Optional<Product> findFirstByProductId(String productId);

}
