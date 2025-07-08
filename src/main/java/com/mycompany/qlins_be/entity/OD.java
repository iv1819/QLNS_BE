/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

/**
 *
 * @author Admin
 */
@Entity 
@Table(name = "ctdh", schema = "dbo")
public class OD {
    @Id // Đánh dấu đây là khóa chính (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")
    private int id;
 @Nationalized   
    @Column(name = "ten_sach")
    private String tenSach;
   @Column(name = "soluong")
    private int soLuong;
   @Column(name = "don_gia")
    private double donGia;
   @Column(name = "tong_tien")
    private double tongTien;
    @Column(name = "ma_dh")
    private String maDH;

    public OD(int id, String tenSach, int soLuong, double donGia, double tongTien, String maDH) {
        this.id = id;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
        this.maDH = maDH;
    }

    public OD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

   
    
}
