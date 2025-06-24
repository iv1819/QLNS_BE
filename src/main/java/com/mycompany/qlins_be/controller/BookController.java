/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Author;
import com.mycompany.qlins_be.entity.Book;
import com.mycompany.qlins_be.entity.Category;
import com.mycompany.qlins_be.entity.Publisher;
import com.mycompany.qlins_be.repository.AuthorRepository;
import com.mycompany.qlins_be.repository.BookRepository;
import com.mycompany.qlins_be.repository.CategoryRepository;
import com.mycompany.qlins_be.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController // Đánh dấu đây là một REST Controller
@RequestMapping("/books") 
@CrossOrigin(origins = "http://localhost:8080")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        // Đảm bảo mã sách được tạo tự động nếu là null hoặc trống
        if (book.getMaSach() == null || book.getMaSach().isEmpty()) {
            book.setMaSach(UUID.randomUUID().toString());
        } else {
            // Nếu mã sách được cung cấp, kiểm tra xem đã tồn tại chưa
            if (bookRepository.existsById(book.getMaSach())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã sách đã tồn tại: " + book.getMaSach());
            }
        }

        /*// --- Xử lý liên kết với Author, Publisher, Category trực tiếp trong Controller ---
        // Tìm Author theo tên. Nếu không tìm thấy, báo lỗi.
        Author author = authorRepository.findByTenTG(book.getTacGia())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Tác giả: " + book.getTacGia() + ". Vui lòng tạo tác giả này trước."));
        book.setTacGia(book.getTacGia());

        // Tìm Publisher theo tên. Nếu không tìm thấy, báo lỗi.
        Publisher publisher = publisherRepository.findByTenNXB(book.getNhaXB())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Nhà xuất bản: " + book.getNhaXB() + ". Vui lòng tạo nhà xuất bản này trước."));
        book.setNhaXB(book.getNhaXB());

        // Tìm Category theo tên. Nếu không tìm thấy, báo lỗi.
        Category category = categoryRepository.findByTenDanhMuc(book.getMaDanhMuc())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Danh mục: " + book.getMaDanhMuc() + ". Vui lòng tạo danh mục này trước."));
        book.setMaDanhMuc(book.getMaDanhMuc());*/

        

        Book newBook = bookRepository.save(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @Valid @RequestBody Book bookDetails) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id));

        // Cập nhật các trường
        existingBook.setTenSach(bookDetails.getTenSach());
        existingBook.setSoLuong(bookDetails.getSoLuong());
        existingBook.setGiaBan(bookDetails.getGiaBan());
        existingBook.setNamXB(bookDetails.getNamXB());
        existingBook.setDuongDanAnh(bookDetails.getDuongDanAnh());
        existingBook.setTacGia(bookDetails.getTacGia());
        existingBook.setMaDanhMuc(bookDetails.getMaDanhMuc());
        existingBook.setNhaXB(bookDetails.getNhaXB());

        /*// --- Xử lý liên kết với Author, Publisher, Category cho cập nhật ---
        // Tìm Author theo tên. Nếu không tìm thấy, báo lỗi.
        Author author = authorRepository.findByTenTG(existingBook.getTacGia())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Tác giả: " + existingBook.getTacGia() + ". Vui lòng tạo tác giả này trước."));
        existingBook.setTacGia(existingBook.getTacGia());

        // Tìm Publisher theo tên. Nếu không tìm thấy, báo lỗi.
        Publisher publisher = publisherRepository.findByTenNXB(existingBook.getNhaXB())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Nhà xuất bản: " + existingBook.getNhaXB() + ". Vui lòng tạo nhà xuất bản này trước."));
        existingBook.setNhaXB(existingBook.getNhaXB());

        // Tìm Category theo tên. Nếu không tìm thấy, báo lỗi.
        Category category = categoryRepository.findByTenDanhMuc(existingBook.getMaDanhMuc())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Danh mục: " + existingBook.getMaDanhMuc() + ". Vui lòng tạo danh mục này trước."));
        existingBook.setMaDanhMuc(existingBook.getMaDanhMuc());*/

        Book updatedBook = bookRepository.save(existingBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id);
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Tìm kiếm sách theo tên sách hoặc tên tác giả.
     * @param query Chuỗi tìm kiếm (có thể là tên sách hoặc tên tác giả).
     * @return Danh sách sách phù hợp.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        // Tìm kiếm theo tên sách hoặc tên tác giả (sử dụng LIKE %query%)
        return ResponseEntity.ok(bookRepository.findByTenSachContainingIgnoreCaseOrTacGiaContainingIgnoreCase(query, query));
    }
}

