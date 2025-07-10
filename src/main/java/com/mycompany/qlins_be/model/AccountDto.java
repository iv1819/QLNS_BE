/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class AccountDto {

    @NotBlank(message = "Tài khoản không được để trống!")
    @Size(min = 3, max = 50, message = "Tài khoản phải có từ 3 đến 50 ký tự!")
    private String taiKhoan; // Use taiKhoan consistently for ID and login

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
    private String matKhau;

    @NotBlank(message = "Chức vụ không được để trống!")
    private String chucVu; // e.g., "Quản lí", "Nhân viên"

    private String trangThai; // e.g., "Yes", "No"

    @NotBlank(message = "Tên nhân viên không được để trống!")
    private String tennv;
}
