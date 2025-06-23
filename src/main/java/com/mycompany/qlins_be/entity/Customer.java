package com.mycompany.qlins_be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

@Entity 
@Table(name = "khachhang", schema = "dbo")
public class Customer {
    @Id
    @UuidGenerator
    @Column(name = "ma_kh")
    private String maKh;
    
    @Nationalized   
    @Column(name = "ten_kh")
    private String tenKh;
    
    @Column(name = "sdt")
    private String sdt;
    
    @Column(name = "email")
    private String email;
    
    @Nationalized   
    @Column(name = "dia_chi")
    private String diaChi;
    
    @Column(name = "ngay_sinh")
    private String ngaySinh;
    
    @Column(name = "gioi_tinh")
    private String gioiTinh;

    // Constructor mặc định
    public Customer() {
    }

    // Constructor với tham số
    public Customer(String maKh, String tenKh, String sdt, String email, String diaChi, String ngaySinh, String gioiTinh) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    // Getters và Setters
    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "maKh='" + maKh + '\'' +
                ", tenKh='" + tenKh + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                '}';
    }
} 