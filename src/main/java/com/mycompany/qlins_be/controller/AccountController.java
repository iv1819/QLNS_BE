/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Account;
import com.mycompany.qlins_be.repository.AccountRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String taiKhoan = request.get("taiKhoan");
        String matKhau = request.get("matKhau");

        Map<String, Object> response = new HashMap<>();

        Optional<Account> accountOpt = accountRepo.findByTaiKhoanAndMatKhau(taiKhoan, matKhau);

        if (accountOpt.isPresent()) {
            Account acc = accountOpt.get();

            if (!acc.getTrangThai().equalsIgnoreCase("Yes")) {
                response.put("success", false);
                response.put("message", "Tài khoản chưa được duyệt");
                return response;
            }
            response.put("success", true);
            response.put("role", acc.getChucVu().equalsIgnoreCase("Quản lí") ? "Quản lí" : "Nhân viên"); 
        } else {
            response.put("success", false);
            response.put("message", "Sai tài khoản hoặc mật khẩu");
        }

        return response;
    }
}

