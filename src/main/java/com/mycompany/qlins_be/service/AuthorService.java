package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.model.AuthorDto;
import com.mycompany.qlins_be.entity.Author;
import com.mycompany.qlins_be.entity.Book; // Import Book entity
import com.mycompany.qlins_be.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // Import UUID
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
     public AuthorDto getAuthorByTenTG(String tenTG) {
        Author author = authorRepository.findByTenTG(tenTG)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với tên: " + tenTG));
        return convertToDto(author);
    }

    public AuthorDto addAuthor(AuthorDto authorDto) {
        // Kiểm tra nếu maTG được cung cấp và không rỗng
        if (authorDto.getMaTG() == null || authorDto.getMaTG().isEmpty()) {
            authorDto.setMaTG(UUID.randomUUID().toString());
        } else if (authorDto.getMaTG() != null && !authorDto.getMaTG().isEmpty()) {
            if (authorRepository.existsById(authorDto.getMaTG())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã tác giả đã tồn tại: " + authorDto.getMaTG());
            }
        }
        // Kiểm tra trùng tên tác giả
        Optional<Author> existingAuthorWithName = authorRepository.findByTenTG(authorDto.getTenTG());
        if (existingAuthorWithName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tác giả với tên '" + authorDto.getTenTG() + "' đã tồn tại.");
        }

        // Chuyển đổi DTO sang Entity. Phương thức convertToEntity sẽ đảm bảo maTG là null nếu rỗng.
        Author author = convertToEntity(authorDto);
        Author newAuthor = authorRepository.save(author); // UuidGenerator sẽ tạo mã nếu maTG là null
        return convertToDto(newAuthor);
    }

    public AuthorDto updateAuthor(String id, AuthorDto authorDetailsDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));

        // Kiểm tra trùng tên tác giả khác ID hiện tại
        Optional<Author> existingAuthorWithName = authorRepository.findByTenTG(authorDetailsDto.getTenTG());
        if (existingAuthorWithName.isPresent() && !existingAuthorWithName.get().getMaTG().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên tác giả '" + authorDetailsDto.getTenTG() + "' đã tồn tại với mã khác.");
        }

        author.setTenTG(authorDetailsDto.getTenTG());
        Author updatedAuthor = authorRepository.save(author);
        return convertToDto(updatedAuthor);
    }

    public void deleteAuthor(String id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tác giả với ID: " + id));

        // Giả sử Author có mối quan hệ @OneToMany với Book
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
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
        String maTgFromDto = authorDto.getMaTG();
        // Nếu mã tác giả từ DTO là null hoặc rỗng, hãy đặt nó là null để UuidGenerator hoạt động
        String finalMaTg = (maTgFromDto == null || maTgFromDto.isEmpty()) ? null : maTgFromDto;
        return new Author(finalMaTg, authorDto.getTenTG()); // Truyền giá trị đã xử lý
    }

    public List<AuthorDto> searchByTenTG(String keyword) {
        return authorRepository.searchByTenTG(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}