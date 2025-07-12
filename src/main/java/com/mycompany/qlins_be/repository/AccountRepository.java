/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Account;

import java.util.List;
import java.util.Optional;

import com.mycompany.qlins_be.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);
    boolean existsByTaiKhoan(String taiKhoan);
    @Query("SELECT a FROM Account a WHERE LOWER(a.taiKhoan) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Account> searchByTaiKhoan(String keyword);
}