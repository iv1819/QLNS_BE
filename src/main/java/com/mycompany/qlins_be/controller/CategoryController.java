/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Category;
import com.mycompany.qlins_be.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/categories") // Tất cả các endpoint trong controller này sẽ bắt đầu bằng /categories
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/findid")
    public ResponseEntity<String> findIdByTenDanhMuc(@RequestParam String tenDanhMuc) {
        return categoryRepository.findByTenDanhMuc(tenDanhMuc)
            .map(category -> ResponseEntity.ok(category.getMaDanhMuc()))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy danh mục với tên: " + tenDanhMuc));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        // Kiểm tra xem danh mục đã tồn tại chưa (theo tên) để tránh trùng lặp
        if (categoryRepository.findByTenDanhMuc(category.getTenDanhMuc()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên danh mục đã tồn tại: " + category.getTenDanhMuc());
        }
        // Nếu maDM không được cung cấp, tự động tạo UUID
        if (category.getMaDanhMuc()== null || category.getMaDanhMuc().isEmpty()) {
            category.setMaDanhMuc(UUID.randomUUID().toString());
        }
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @Valid @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + id));

        // Kiểm tra trùng tên với các danh mục khác (trừ chính danh mục đang cập nhật)
        Optional<Category> existingCategoryWithSameName = categoryRepository.findByTenDanhMuc(categoryDetails.getTenDanhMuc());
        if (existingCategoryWithSameName.isPresent() && !existingCategoryWithSameName.get().getMaDanhMuc().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên danh mục đã tồn tại: " + categoryDetails.getTenDanhMuc());
        }

        category.setTenDanhMuc(categoryDetails.getTenDanhMuc());
        Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + id);
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint để lấy chỉ tên danh mục cho ComboBox ở frontend
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllCategoryNames() {
        List<String> names = categoryRepository.findAll().stream() // Gọi trực tiếp repository
                                      .map(Category::getTenDanhMuc)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }
}
