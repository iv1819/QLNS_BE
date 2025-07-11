/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.model.BookDto;
import com.mycompany.qlins_be.service.BookService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@RestController // Đánh dấu đây là một REST Controller
@RequestMapping("/books") 
@CrossOrigin(origins = "http://localhost:8080")
public class BookController {

   @Autowired
    private BookService bookService; // Tiêm BookService

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable String id) {
        BookDto bookDto = bookService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }
 @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
        // Validation (kiểm tra BindingResult) vẫn nên ở Controller
        // vì nó thuộc về lớp giao diện (Web Layer)
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // Gọi phương thức addBook từ BookService
        BookDto savedBook = bookService.addBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @Valid @RequestBody BookDto bookDetails, BindingResult bindingResult) {
        // Validation (kiểm tra BindingResult) vẫn nên ở Controller
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // Gọi phương thức updateBook từ BookService
        BookDto updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }


   // Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        // Service đã ném ResponseStatusException nếu không tìm thấy, Controller chỉ cần gọi
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
@GetMapping("/categories/{maDM}") 
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable String maDM) {
        List<BookDto> books = bookService.getBooksByMaDM(maDM); // Gọi hàm đã sửa trong BookService
        // Spring sẽ tự động trả về HttpStatus.OK (200) và danh sách sách (có thể rỗng)
        return ResponseEntity.ok(books);
    }
    /**
     * Tìm kiếm sách theo tên sách hoặc tên tác giả.
     * @param query Chuỗi tìm kiếm (có thể là tên sách hoặc tên tác giả).
     * @return Danh sách sách phù hợp (List<BookDto>).
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam String query) { // Thay đổi kiểu trả về
        List<BookDto> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
}
