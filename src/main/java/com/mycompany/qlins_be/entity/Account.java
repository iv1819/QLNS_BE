/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;

/**
 *
 * @author Admin
 */
@Entity 
@Table(name = "taikhoan", schema = "dbo")
public class Account {
    @Id // Đánh dấu đây là khóa chính (Primary Key)
    @Nationalized 
    @Column(name = "tai_khoan")
    private String taiKhoan;
 
    @Column(name = "mat_khau")
    private String matKhau;
    @Nationalized   
   @Column(name = "ten_cv")
    private String chucVu;
   @Column(name = "trang_thai")
    private String trangThai;

    public Account() {
    }

    public Account(String taiKhoan, String matKhau, String chucVu, String trangThai) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
   
}
