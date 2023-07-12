package com.sumber.barokah.jurnal.repository;

import com.sumber.barokah.jurnal.entity.master.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    //query method
    Optional<Customer> findFirstByCustomerId(String id);

}
