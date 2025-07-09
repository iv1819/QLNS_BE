package com.mycompany.qlins_be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity 
@Table(name = "nhanvien", schema = "dbo")
public class Employee {
    @Id
    @Column(name = "ma_nv")
    private String maNv;
    
    @Nationalized   
    @Column(name = "ten_nv")
    private String tenNv;
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Column(name = "ngay_vao_lam")
    private LocalDate ngayVaoLam;
    
    @Column(name = "sdt")
    private String sdt;
    
    @Column(name = "luong")
    private BigDecimal luong;

    @Column(name = "ten_cv")
    private String tenCv;

    // Constructor mặc định
    public Employee() {
    }

    // Constructor với tham số
    public Employee(String maNv, String tenNv, LocalDate ngaySinh, LocalDate ngayVaoLam, String sdt, BigDecimal luong, String tenCv) {
        this.maNv = maNv;
        this.tenNv = tenNv;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
        this.sdt = sdt;
        this.luong = luong;
        this.tenCv = tenCv;
    }

    // Getters và Setters
    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public BigDecimal getLuong() {
        return luong;
    }

    public void setLuong(BigDecimal luong) {
        this.luong = luong;
    }

    public String getTenCv() {
        return tenCv;
    }
    public void setTenCv(String tenCv) {
        this.tenCv = tenCv;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "maNv='" + maNv + '\'' +
                ", tenNv='" + tenNv + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", ngayVaoLam=" + ngayVaoLam +
                ", sdt='" + sdt + '\'' +
                ", luong=" + luong +
                ", tenCv='" + tenCv + '\'' +
                '}';
    }
} 