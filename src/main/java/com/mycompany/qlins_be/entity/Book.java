/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Entity; // Sử dụng jakarta.persistence cho Spring Boot 3+
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

@Entity 
@Table(name = "sach", schema = "dbo")
public class Book {
    @Id // Đánh dấu đây là khóa chính (Primary Key)
    @UuidGenerator
    @Column(name = "ma_sach") // Ánh xạ tới cột 'book_id' trong CSDL
    private String maSach; // Tên biến để khớp với frontend
 @Nationalized   
    @Column(name = "ten_sach")
    private String tenSach;
   @Column(name = "so_luong")
    private int soLuong;
   @Column(name = "gia")
    private double giaBan;
    @Nationalized   
   @Column(name = "ten_tg")
    private String tacGia;
     @Nationalized   
@Column(name = "ten_nxb")
    private String nhaXB;

    @Column(name = "anh")
    private String duongDanAnh; 
    @Column(name = "nam_xb")
    private int namXB;
 @Nationalized   
    @Column(name = "ten_dm") // Khóa ngoại tới bảng Category
    private String maDanhMuc;

    // Constructor mặc định (cần thiết cho JPA và Jackson)
    public Book() {
        // Có thể gán giá trị mặc định nếu cần
        this.duongDanAnh = "";
    }

    // Constructor với các tham số (tùy chọn, Lombok có thể thay thế)
    public Book(String maSach, String tenSach, String duongDanAnh, String nhaXB, String tacGia, int soLuong, double giaBan, int namXB, String maDanhMuc) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.duongDanAnh = duongDanAnh;
        this.nhaXB = nhaXB;
        this.tacGia = tacGia;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.namXB = namXB;
        this.maDanhMuc = maDanhMuc;
    }

    // --- Getters và Setters (Bắt buộc cho JPA và Jackson) ---
    // NetBeans có thể tự tạo bằng cách: Click chuột phải -> Insert Code -> Getter and Setter

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getDuongDanAnh() {
        return duongDanAnh;
    }

    public void setDuongDanAnh(String duongDanAnh) {
        this.duongDanAnh = duongDanAnh;
    }

    public String getNhaXB() {
        return nhaXB;
    }

    public void setNhaXB(String nhaXB) {
        this.nhaXB = nhaXB;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getNamXB() {
        return namXB;
    }

    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    @Override
    public String toString() {
        return "Book{" +
                "maSach='" + maSach + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", duongDanAnh='" + duongDanAnh + '\'' +
                ", nhaXB='" + nhaXB + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                ", namXB=" + namXB +
                ", maDanhMuc='" + maDanhMuc + '\'' +
                '}';
    }
}

