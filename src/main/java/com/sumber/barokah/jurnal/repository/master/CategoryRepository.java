package com.sumber.barokah.jurnal.repository.master;

import com.sumber.barokah.jurnal.entity.master.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

    // query method
    Optional<Category> findFirstByCategoryId(String id);


}
