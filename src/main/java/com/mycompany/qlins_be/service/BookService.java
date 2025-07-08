/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;


import com.mycompany.qlins_be.entity.Book;
import com.mycompany.qlins_be.model.BookDto;
import com.mycompany.qlins_be.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // Đánh dấu đây là một Spring Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Lấy tất cả sách
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Lấy sách theo ID
    public BookDto getBookById(String id) {
        return bookRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id));
    }

    // Thêm sách mới
    public BookDto addBook(BookDto bookDto) {
        // Đảm bảo mã sách được tạo tự động nếu là null hoặc trống
        if (bookDto.getMaSach() == null || bookDto.getMaSach().isEmpty()) {
            bookDto.setMaSach(UUID.randomUUID().toString());
        } else {
            // Nếu mã sách được cung cấp, kiểm tra xem đã tồn tại chưa
            if (bookRepository.existsById(bookDto.getMaSach())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã sách đã tồn tại: " + bookDto.getMaSach());
            }
        }
        Optional<Book> existingBookWithName = bookRepository.findByTenSach(bookDto.getTenSach());
        if (existingBookWithName.isPresent()) {
            // Nếu tìm thấy sách có cùng tên, ném ra ngoại lệ CONFLICT
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sách với tên '" + bookDto.getTenSach() + "' đã tồn tại.");
        }
        Book book = convertToEntity(bookDto);
        Book newBook = bookRepository.save(book);
        return convertToDto(newBook);
    }

    // Cập nhật sách
    public BookDto updateBook(String id, BookDto bookDetailsDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id));

        // Cập nhật các trường từ DTO
        existingBook.setTenSach(bookDetailsDto.getTenSach());
        existingBook.setSoLuong(bookDetailsDto.getSoLuong());
        existingBook.setGiaBan(bookDetailsDto.getGiaBan());
        existingBook.setNamXB(bookDetailsDto.getNamXB());
        existingBook.setDuongDanAnh(bookDetailsDto.getDuongDanAnh());
        existingBook.setTacGia(bookDetailsDto.getTacGia());
        existingBook.setMaDanhMuc(bookDetailsDto.getMaDanhMuc());
        existingBook.setNhaXB(bookDetailsDto.getNhaXB());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDto(updatedBook);
    }

    // Xóa sách
    public void deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Tìm kiếm sách theo tên sách hoặc tên tác giả
    public List<BookDto> searchBooks(String query) {
        return bookRepository.findByTenSachContainingIgnoreCaseOrTacGiaContainingIgnoreCase(query, query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Phương thức chuyển đổi Entity sang DTO
    private BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setMaSach(book.getMaSach());
        bookDto.setTenSach(book.getTenSach());
        bookDto.setSoLuong(book.getSoLuong());
        bookDto.setGiaBan(book.getGiaBan());
        bookDto.setTacGia(book.getTacGia());
        bookDto.setNhaXB(book.getNhaXB());
        bookDto.setDuongDanAnh(book.getDuongDanAnh());
        bookDto.setNamXB(book.getNamXB());
        bookDto.setMaDanhMuc(book.getMaDanhMuc());
        return bookDto;
    }

    // Phương thức chuyển đổi DTO sang Entity
    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setMaSach(bookDto.getMaSach());
        book.setTenSach(bookDto.getTenSach());
        book.setSoLuong(bookDto.getSoLuong());
        book.setGiaBan(bookDto.getGiaBan());
        book.setTacGia(bookDto.getTacGia());
        book.setNhaXB(bookDto.getNhaXB());
        book.setDuongDanAnh(bookDto.getDuongDanAnh());
        book.setNamXB(bookDto.getNamXB());
        book.setMaDanhMuc(bookDto.getMaDanhMuc());
        return book;
    }
}
