/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.model.AccountDto;
import com.mycompany.qlins_be.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/accounts")
public class AccountController {

   @Autowired
    private AccountService accountService;

     @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String taiKhoan = request.get("taiKhoan"); // Use taiKhoan directly
        String matKhau = request.get("matKhau");

        Map<String, Object> response = new HashMap<>();
        try {
            AccountDto accountDto = accountService.loginAccount(taiKhoan, matKhau);
            response.put("success", true);
            response.put("role", accountDto.getChucVu().equalsIgnoreCase("Quản lí") ? "Quản lí" : "Nhân viên");
            response.put("tennv", accountDto.getTennv());
        } catch (ResponseStatusException e) {
            response.put("success", false);
            response.put("message", e.getReason());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
        return response;
    }
     @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto accountDto, BindingResult bindingResult) {
        // Xử lý lỗi validation từ @Valid
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            AccountDto registeredAccount = accountService.registerAccount(accountDto);
            return new ResponseEntity<>(registeredAccount, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            // Bắt các ngoại lệ ResponseStatusException ném ra từ service
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getReason());
            return new ResponseEntity<>(errorResponse, e.getStatusCode());
        } catch (Exception e) {
            // Xử lý các ngoại lệ không xác định
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi nội bộ server: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all accounts (query)
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Endpoint to get an account by taiKhoan (query)
    @GetMapping("/{taiKhoan}")
    public ResponseEntity<AccountDto> getAccountByTaiKhoan(@PathVariable String taiKhoan) {
        AccountDto account = accountService.getAccountByTaiKhoan(taiKhoan);
        return ResponseEntity.ok(account);
    }

    // Endpoint to update an account
    @PutMapping("/{taiKhoan}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String taiKhoan, @Valid @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccount(taiKhoan, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    // Endpoint to delete an account
    @DeleteMapping("/{taiKhoan}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String taiKhoan) {
        accountService.deleteAccount(taiKhoan);
        return ResponseEntity.noContent().build();
    }
}

