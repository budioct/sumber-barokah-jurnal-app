package com.sumber.barokah.jurnal.repository;

import com.sumber.barokah.jurnal.entity.master.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
