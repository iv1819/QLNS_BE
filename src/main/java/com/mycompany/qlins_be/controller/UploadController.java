/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/uploads")      // POST /api/uploads
public class UploadController {

    @Value("${upload.dir}")
    private String uploadDir;    // v.d. "uploads"

    @PostMapping                   // multipart field "file"
    public Map<String, String> upload(@RequestParam MultipartFile file) throws IOException {

        if (file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File trống");

        String ext = Optional.ofNullable(
                StringUtils.getFilenameExtension(file.getOriginalFilename())
        ).map(e -> "." + e).orElse("");

        String storedName = UUID.randomUUID() + ext;              // 4e2c.jpg
        Path   dst        = Paths.get(uploadDir).resolve(storedName);
        file.transferTo(dst);

        /* ---------- TRẢ JSON GỌN ---------- */
        return Map.of(
                "url",  "/api/uploads/" + storedName,             // client dùng ngay
                "name", storedName,
                "orig", Objects.requireNonNull(file.getOriginalFilename())
        );
    }
}

