/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Book;
import com.mycompany.qlins_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository; 
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
    // Bạn có thể định nghĩa các phương thức tìm kiếm tùy chỉnh ở đây theo quy ước đặt tên của Spring Data JPA
    List<Book> findByMaDanhMuc(String maDanhMuc); // Tìm sách theo mã danh mục
    List<Book> findByTenSachContainingIgnoreCase(String tenSach); // Tìm sách theo tên (tìm kiếm mờ)
    List<Book> findByTenSachContainingIgnoreCaseOrTacGiaContainingIgnoreCase(String tenSach, String tenTacGia);
}
