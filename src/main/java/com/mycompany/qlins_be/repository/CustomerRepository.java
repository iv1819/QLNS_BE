package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsBySdt(String sdt);
    Customer findBySdt(String sdt);
    @Query("SELECT c FROM Customer c ORDER BY c.maKh DESC")
    List<Customer> findAllOrderByMaKhDesc();
} 