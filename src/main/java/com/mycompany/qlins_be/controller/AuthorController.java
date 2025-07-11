/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.model.AuthorDto;
import com.mycompany.qlins_be.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/authors")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
@GetMapping("/findid")
    public ResponseEntity<AuthorDto> getAuthorByTenTG(@RequestParam String tenTG) {
        AuthorDto authorDto = authorService.getAuthorByTenTG(tenTG);
        return ResponseEntity.ok(authorDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable String id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto newAuthor = authorService.addAuthor(authorDto);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable String id, @Valid @RequestBody AuthorDto authorDto) {
        AuthorDto updatedAuthor = authorService.updateAuthor(id, authorDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorDto>> searchAuthors(@RequestParam String keyword) {
        List<AuthorDto> authors = authorService.searchByTenTG(keyword);
        return ResponseEntity.ok(authors);
    }

    // Endpoint để lấy chỉ tên tác giả cho ComboBox ở frontend
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllAuthorNames() {
        List<String> names = authorService.getAllAuthorNames(); // Gọi qua Service
        return ResponseEntity.ok(names);
    }

}