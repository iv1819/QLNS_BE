/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Admin
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    Optional<Publisher> findByTenNXB(String tenNXB); // Tìm nhà xuất bản theo tên
}
