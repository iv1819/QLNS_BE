/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Admin
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findByTenTG(String tenTG); // Tìm tác giả theo tên
    @Query("SELECT a FROM Author a WHERE LOWER(a.tenTG) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Author> searchByTenTG(String keyword);
}
