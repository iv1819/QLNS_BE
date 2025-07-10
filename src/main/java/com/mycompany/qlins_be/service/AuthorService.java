/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;

/**
 *
 * @author trang
 */
import com.mycompany.qlins_be.model.AuthorDto;
import com.mycompany.qlins_be.entity.Author;
import com.mycompany.qlins_be.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(String id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));
        return convertToDto(author);
    }

    public AuthorDto addAuthor(AuthorDto authorDto) {
        // Kiểm tra xem tác giả đã tồn tại chưa (theo tên) để tránh trùng lặp
        if (authorRepository.findByTenTG(authorDto.getTenTG()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên tác giả đã tồn tại: " + authorDto.getTenTG());
        }

        Author author = convertToEntity(authorDto);
        // Nếu maTG không được cung cấp, tự động tạo UUID
        if (author.getMaTG() == null || author.getMaTG().isEmpty()) {
            author.setMaTG(UUID.randomUUID().toString());
        }
        Author savedAuthor = authorRepository.save(author);
        return convertToDto(savedAuthor);
    }

    public AuthorDto updateAuthor(String id, AuthorDto authorDetailsDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));

        // Kiểm tra trùng tên với các tác giả khác (trừ chính tác giả đang cập nhật)
        Optional<Author> existingAuthorWithSameName = authorRepository.findByTenTG(authorDetailsDto.getTenTG());
        if (existingAuthorWithSameName.isPresent() && !existingAuthorWithSameName.get().getMaTG().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên tác giả đã tồn tại: " + authorDetailsDto.getTenTG());
        }

        author.setTenTG(authorDetailsDto.getTenTG());
        Author updatedAuthor = authorRepository.save(author);
        return convertToDto(updatedAuthor);
    }

    public void deleteAuthor(String id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));

        if (!author.getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể xóa vì tác giả vẫn có sách liên kết.");
        }

        authorRepository.delete(author);
    }


    public List<String> getAllAuthorNames() {
        return authorRepository.findAll().stream()
                .map(Author::getTenTG)
                .collect(Collectors.toList());
    }

    // Helper method to convert Entity to DTO
    private AuthorDto convertToDto(Author author) {
        return new AuthorDto(author.getMaTG(), author.getTenTG());
    }

    // Helper method to convert DTO to Entity
    private Author convertToEntity(AuthorDto authorDto) {
        return new Author(authorDto.getMaTG(), authorDto.getTenTG());
    }

    public List<AuthorDto> searchByTenTG(String keyword) {
        return authorRepository.searchByTenTG(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}