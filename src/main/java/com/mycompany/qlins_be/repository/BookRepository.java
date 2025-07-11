/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Book;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Import Query
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
@Repository // Đánh dấu đây là một Spring Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // JpaRepository cung cấp sẵn các phương thức CRUD cơ bản:
    // save(), findById(), findAll(), deleteById(), v.v.
Optional<Book> findByTenSach(String tenSach);
    List<Book> findByTenSachContainingIgnoreCase(String tenSach); 
       List<Book> findByTenSachContainingIgnoreCaseOrAuthorTenTGContainingIgnoreCase(String tenSach, String tenTacGia);

     List<Book> findByDm_MaDanhMuc(String maDanhMuc);

}
