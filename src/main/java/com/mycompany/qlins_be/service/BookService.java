/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;


import com.mycompany.qlins_be.entity.Author;
import com.mycompany.qlins_be.entity.Book;
import com.mycompany.qlins_be.entity.Category;
import com.mycompany.qlins_be.entity.Publisher;
import com.mycompany.qlins_be.model.BookDto;
import com.mycompany.qlins_be.repository.AuthorRepository;
import com.mycompany.qlins_be.repository.BookRepository;
import com.mycompany.qlins_be.repository.CategoryRepository;
import com.mycompany.qlins_be.repository.PublisherRepository;
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
    private final BookRepository bookRepository;
 @Autowired
    private final AuthorRepository authorRepository;
  @Autowired
    private final PublisherRepository pubRepo;
  @Autowired
    private final CategoryRepository caRepo;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository pubRepo, CategoryRepository caRepo) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.pubRepo = pubRepo;
        this.caRepo = caRepo;
    }
  
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
  public List<BookDto> getBooksByMaDM(String maDM) { // Đổi tên để rõ ràng hơn và đổi kiểu trả về
        List<Book> books = bookRepository.findByDm_MaDanhMuc(maDM);
        // Chuyển đổi danh sách Book entity sang danh sách BookDto
        return books.stream()
                    .map(this::convertToDto)
                    .collect(java.util.stream.Collectors.toList());
    }
    // Thêm sách mới
    public BookDto addBook(BookDto bookDto) {
        if (bookDto.getMaSach() == null || bookDto.getMaSach().isEmpty()) {
            bookDto.setMaSach(UUID.randomUUID().toString());
        } else {
            if (bookRepository.existsById(bookDto.getMaSach())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã sách đã tồn tại: " + bookDto.getMaSach());
            }
        }
        Optional<Book> existingBookWithName = bookRepository.findByTenSach(bookDto.getTenSach());
        if (existingBookWithName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sách với tên '" + bookDto.getTenSach() + "' đã tồn tại.");
        }

        Book book = convertToEntity(bookDto); // Uses updated convertToEntity
        Book newBook = bookRepository.save(book);
        return convertToDto(newBook);
    }

    // Cập nhật sách
     public BookDto updateBook(String id, BookDto bookDetailsDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sách với ID: " + id));

        // Find the Author by ID
        Author author = authorRepository.findById(bookDetailsDto.getMaTacGia())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + bookDetailsDto.getMaTacGia()));
        Publisher nxb = pubRepo.findById(bookDetailsDto.getMaNXB())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy NXB với ID: " + bookDetailsDto.getMaNXB()));
        Category dm = caRepo.findById(bookDetailsDto.getMaDanhMuc())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + bookDetailsDto.getMaDanhMuc()));
        existingBook.setTenSach(bookDetailsDto.getTenSach());
        existingBook.setSoLuong(bookDetailsDto.getSoLuong());
        existingBook.setGiaBan(bookDetailsDto.getGiaBan());
        existingBook.setNamXB(bookDetailsDto.getNamXB());
        existingBook.setDuongDanAnh(bookDetailsDto.getDuongDanAnh());
        existingBook.setAuthor(author); // Set the Author object
        existingBook.setDm(dm);
        existingBook.setNxb(nxb);

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
        return bookRepository.findByTenSachContainingIgnoreCaseOrAuthorTenTGContainingIgnoreCase(query, query).stream()
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
        // Set author details from the Author object
        if (book.getAuthor() != null) {
            bookDto.setMaTacGia(book.getAuthor().getMaTG());
            bookDto.setTenTacGia(book.getAuthor().getTenTG());
        }
        if (book.getNxb()!= null) {
            bookDto.setMaNXB(book.getNxb().getMaNXB());
            bookDto.setTenNXB(book.getNxb().getTenNXB());
        } 
        if (book.getDm()!= null) {
            bookDto.setMaDanhMuc(book.getDm().getMaDanhMuc());
            bookDto.setTenDanhMuc(book.getDm().getTenDanhMuc());
        } 
        bookDto.setDuongDanAnh(book.getDuongDanAnh());
        bookDto.setNamXB(book.getNamXB());
        return bookDto;
    }

    // Phương thức chuyển đổi DTO sang Entity
    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        String masach = bookDto.getMaSach();
        String finalmaSach = (masach == null || masach.isEmpty()) ? null : masach;
        book.setMaSach(finalmaSach);
        book.setTenSach(bookDto.getTenSach());
        book.setSoLuong(bookDto.getSoLuong());
        book.setGiaBan(bookDto.getGiaBan());
        // Find Author by ID from DTO and set it to the entity
        if (bookDto.getMaTacGia() != null && !bookDto.getMaTacGia().isEmpty()) {
            Author author = authorRepository.findById(bookDto.getMaTacGia())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + bookDto.getMaTacGia()));
            book.setAuthor(author);
        }
 if (bookDto.getMaNXB()!= null && !bookDto.getMaNXB().isEmpty()) {
            Publisher nxb = pubRepo.findById(bookDto.getMaNXB())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy NXB với ID: " + bookDto.getMaNXB()));
            book.setNxb(nxb);
        } 
  if (bookDto.getMaDanhMuc()!= null && !bookDto.getMaDanhMuc().isEmpty()) {
            Category dm = caRepo.findById(bookDto.getMaDanhMuc())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + bookDto.getMaDanhMuc()));
            book.setDm(dm);
        } 
 book.setDuongDanAnh(bookDto.getDuongDanAnh());
        book.setNamXB(bookDto.getNamXB());
        return book;
    }
}
