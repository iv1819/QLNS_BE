/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Entity; // Sử dụng jakarta.persistence cho Spring Boot 3+
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
       @ManyToOne
    @JoinColumn(name = "ma_tg", referencedColumnName = "ma_tg") // 'ma_tg' is the foreign key column in 'sach' table
    private Author author;
    @ManyToOne
    @JoinColumn(name = "ma_nxb", referencedColumnName = "ma_nxb") // 'ma_tg' is the foreign key column in 'sach' table
    private Publisher nxb;

    @Column(name = "anh")
    private String duongDanAnh; 
    @Column(name = "nam_xb")
    private int namXB;
@ManyToOne
    @JoinColumn(name = "ma_dm", referencedColumnName = "ma_dm") // 'ma_tg' is the foreign key column in 'sach' table
    private Category dm;

    public Book() {
    }

    // Updated constructor to use Author object
    public Book(String maSach, String tenSach, String duongDanAnh, Publisher nxb, Author author, int soLuong, double giaBan, int namXB, Category maDanhMuc) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.duongDanAnh = duongDanAnh;
        this.nxb = nxb;
        this.author = author; // Assign the Author object
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.namXB = namXB;
        this.dm = maDanhMuc;
    }

    // Getters and Setters (updated for Author object)
    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public String getDuongDanAnh() { return duongDanAnh; }
    public void setDuongDanAnh(String duongDanAnh) { this.duongDanAnh = duongDanAnh; }

    public Publisher getNxb() {
        return nxb;
    }

    public void setNxb(Publisher nxb) {
        this.nxb = nxb;
    }


    // New getter/setter for Author object
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getNamXB() { return namXB; }
    public void setNamXB(int namXB) { this.namXB = namXB; }

    public Category getDm() {
        return dm;
    }

    public void setDm(Category dm) {
        this.dm = dm;
    }
   
   @Override
    public String toString() {
        return "Book{" +
                "maSach='" + maSach + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", duongDanAnh='" + duongDanAnh + '\'' +
                ", nhaXB='" + (nxb != null ? nxb.getTenNXB(): "null") + '\'' +
                ", author=" + (author != null ? author.getTenTG() : "null") + // Updated for Author
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                ", namXB=" + namXB +
                ", maDanhMuc='" + (dm != null ? dm.getTenDanhMuc(): "null") + 
                '}';
    }
}

