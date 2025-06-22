/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Author;
import com.mycompany.qlins_be.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/authors")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository; // Tương tác trực tiếp với Repository

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@Valid @RequestBody Author author) {
        // Kiểm tra xem tác giả đã tồn tại chưa (theo tên) để tránh trùng lặp
        if (authorRepository.findByTenTG(author.getTenTG()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên tác giả đã tồn tại: " + author.getTenTG());
        }
        // Nếu maTG không được cung cấp, tự động tạo UUID
        if (author.getMaTG() == null || author.getMaTG().isEmpty()) {
            author.setMaTG(UUID.randomUUID().toString());
        }
        Author newAuthor = authorRepository.save(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @Valid @RequestBody Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));

        // Kiểm tra trùng tên với các tác giả khác (trừ chính tác giả đang cập nhật)
        Optional<Author> existingAuthorWithSameName = authorRepository.findByTenTG(authorDetails.getTenTG());
        if (existingAuthorWithSameName.isPresent() && !existingAuthorWithSameName.get().getMaTG().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên tác giả đã tồn tại: " + authorDetails.getTenTG());
        }

        author.setTenTG(authorDetails.getTenTG());
        Author updatedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        if (!authorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id);
        }
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint để lấy chỉ tên tác giả cho ComboBox ở frontend
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllAuthorNames() {
        List<String> names = authorRepository.findAll().stream() // Gọi trực tiếp repository
                                     .map(Author::getTenTG)
                                     .collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }
}
