/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Book;
import com.mycompany.qlins_be.entity.OD;
import com.mycompany.qlins_be.repository.BookRepository;
import com.mycompany.qlins_be.repository.ODRepository;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@RestController // Đánh dấu đây là một REST Controller
@RequestMapping("/ods") 
@CrossOrigin(origins = "http://localhost:8080")
public class ODController {

    @Autowired
    private ODRepository odRepository;

    @GetMapping
    public ResponseEntity<List<OD>> getAllODs() {
        List<OD> ods = odRepository.findAll();
        return ResponseEntity.ok(ods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OD> getODbyID(@PathVariable int id) {
        return odRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy ctdh với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<OD> addOD(@Valid @RequestBody OD od) {
        OD newod = odRepository.save(od);
        return new ResponseEntity<>(newod, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOD(@PathVariable int id) {
        if (!odRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy ctdh với ID: " + id);
        }
        odRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
@DeleteMapping("/madh/{maDH}")
public ResponseEntity<Void> deleteByMaDH(@PathVariable String maDH) {
    // Nếu muốn kiểm tra tồn tại trước:
    if (!odRepository.existsByMaDH(maDH)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy CTDH nào với mã đơn hàng: " + maDH);
    }

    odRepository.deleteByMaDH(maDH);
    return ResponseEntity.noContent().build(); // HTTP 204
}

    @GetMapping("/search")
    public ResponseEntity<List<OD>> searchODs(@RequestParam String query) {
        // Tìm kiếm theo tên sách hoặc tên tác giả (sử dụng LIKE %query%)
        return ResponseEntity.ok(odRepository.findByMaDH(query));
    }
}

