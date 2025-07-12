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

    @NotBlank(message = "Lặp lại mật khẩu không được để trống!")
    private String confirmPassword;

    @NotBlank(message = "Chức vụ không được để trống!")
    private String chucVu; // e.g., "Quản lí", "Nhân viên"

    private String trangThai; // e.g., "Yes", "No"

    @NotBlank(message = "Tên nhân viên không được để trống!")
    private String tennv;

    public @NotBlank(message = "Tài khoản không được để trống!") @Size(min = 3, max = 50, message = "Tài khoản phải có từ 3 đến 50 ký tự!") String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(@NotBlank(message = "Tài khoản không được để trống!") @Size(min = 3, max = 50, message = "Tài khoản phải có từ 3 đến 50 ký tự!") String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public @NotBlank(message = "Mật khẩu không được để trống!") @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!") String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(@NotBlank(message = "Mật khẩu không được để trống!") @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!") String matKhau) {
        this.matKhau = matKhau;
    }

    public @NotBlank(message = "Lặp lại mật khẩu không được để trống!") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Lặp lại mật khẩu không được để trống!") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public @NotBlank(message = "Chức vụ không được để trống!") String getChucVu() {
        return chucVu;
    }

    public void setChucVu(@NotBlank(message = "Chức vụ không được để trống!") String chucVu) {
        this.chucVu = chucVu;
    }

    public @NotBlank(message = "Tên nhân viên không được để trống!") String getTennv() {
return tennv;
    }

    public void setTennv(@NotBlank(message = "Tên nhân viên không được để trống!") String tennv) {
        this.tennv = tennv;
    }
}