/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;


import com.mycompany.qlins_be.model.ODDto;
import com.mycompany.qlins_be.service.ODService;
import jakarta.validation.Valid;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * REST Controller để quản lý các Chi tiết Đơn Hàng (OD - Order Detail).
 * Sử dụng tầng Service để xử lý logic nghiệp vụ và DTO cho việc truyền tải dữ liệu.
 */
@RestController
@RequestMapping("/ods")
@CrossOrigin(origins = "http://localhost:8080")
public class ODController {

    private final ODService odService; // Inject ODServiceImpl

    @Autowired
    public ODController(ODService odService) {
        this.odService = odService;
    }

    @GetMapping
    public ResponseEntity<List<ODDto>> getAllODs() {
        List<ODDto> ods = odService.getAllODs();
        return ResponseEntity.ok(ods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ODDto> getODById(@PathVariable int id) {
        return odService.getODById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy chi tiết đơn hàng với ID: " + id));
    }

     @PostMapping
    public ResponseEntity<?> addOD(@Valid @RequestBody ODDto odDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ODDto newOd = odService.addOD(odDto);
        return new ResponseEntity<>(newOd, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOD(@PathVariable int id) {
        try {
            odService.deleteOD(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/madh/{maDH}")
    public ResponseEntity<Void> deleteODByMaDH(@PathVariable String maDH) {
        try {
            odService.deleteODByMaDH(maDH);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Xử lý lỗi nếu có, ví dụ: đơn hàng không tồn tại
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lỗi khi xóa chi tiết đơn hàng theo mã đơn hàng: " + maDH + ". " + e.getMessage());
        }
    }

    @GetMapping("/byMaDH/{maDH}")
    public ResponseEntity<List<ODDto>> getODsByMaDH(@PathVariable String maDH) {
        List<ODDto> ods = odService.getODsByMaDH(maDH);
        if (ods.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy chi tiết đơn hàng nào cho mã đơn hàng: " + maDH);
        }
        return ResponseEntity.ok(ods);
    }

}
