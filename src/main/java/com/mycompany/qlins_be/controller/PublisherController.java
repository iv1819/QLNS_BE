/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Publisher;
import com.mycompany.qlins_be.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publishers")
@CrossOrigin(origins = "http://localhost:8080")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository; // Tương tác trực tiếp với Repository

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        return ResponseEntity.ok(publishers);
    }
 @GetMapping("/findid")
    public ResponseEntity<String> findIdByTenNXB(@RequestParam String tenNXB) {
        return publisherRepository.findByTenNXB(tenNXB)
            .map(publisher -> ResponseEntity.ok(publisher.getMaNXB()))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy NXB với tên: " + tenNXB));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable String id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhà xuất bản với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@Valid @RequestBody Publisher publisher) {
        // Kiểm tra xem nhà xuất bản đã tồn tại chưa (theo tên) để tránh trùng lặp
        if (publisherRepository.findByTenNXB(publisher.getTenNXB()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên nhà xuất bản đã tồn tại: " + publisher.getTenNXB());
        }
        // Nếu maNXB không được cung cấp, tự động tạo UUID
        if (publisher.getMaNXB() == null || publisher.getMaNXB().isEmpty()) {
            publisher.setMaNXB(UUID.randomUUID().toString());
        }
        Publisher newPublisher = publisherRepository.save(publisher);
        return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable String id, @Valid @RequestBody Publisher publisherDetails) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhà xuất bản với ID: " + id));

        // Kiểm tra trùng tên với các NXB khác (trừ chính NXB đang cập nhật)
        Optional<Publisher> existingPublisherWithSameName = publisherRepository.findByTenNXB(publisherDetails.getTenNXB());
        if (existingPublisherWithSameName.isPresent() && !existingPublisherWithSameName.get().getMaNXB().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên nhà xuất bản đã tồn tại: " + publisherDetails.getTenNXB());
        }

        publisher.setTenNXB(publisherDetails.getTenNXB());
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable String id) {
        if (!publisherRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhà xuất bản với ID: " + id);
        }
        publisherRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint để lấy chỉ tên nhà xuất bản cho ComboBox ở frontend
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllPublisherNames() {
        List<String> names = publisherRepository.findAll().stream() // Gọi trực tiếp repository
                                      .map(Publisher::getTenNXB)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }
}
