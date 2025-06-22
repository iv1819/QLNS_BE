/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "danh_muc")
public class Category {
    @Id
    @UuidGenerator // TỰ ĐỘNG TẠO UUID CHO TRƯỜNG maDanhMuc KHI LƯU MỚI
    @Column(name = "ma_dm") // Ánh xạ tới cột MaDM trong bảng DanhMuc
    private String maDanhMuc; // Tên biến trong Java
 @Nationalized   
    @Column(name = "ten_dm") // Ánh xạ tới cột TenDM trong bảng DanhMuc
    private String tenDanhMuc;

    // Constructor mặc định (cần thiết cho JPA)
    public Category() {
    }

    // Constructor với tham số (sẽ dùng khi tạo đối tượng từ dữ liệu có sẵn, ví dụ từ database)
    // Khi thêm mới, bạn có thể không cần truyền maDanhMuc vào constructor này, Hibernate sẽ tự tạo
    public Category(String maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    // --- Getters và Setters ---

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    @Override
    public String toString() {
        return "Category{" +
                "maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                '}';
    }
}
