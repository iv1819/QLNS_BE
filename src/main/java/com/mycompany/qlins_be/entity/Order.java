/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

/**
 *
 * @author Admin
 */
@Entity 
@Table(name = "donhang", schema = "dbo")
public class Order {
    @Id 
    @Column(name = "ma_dh")
    private String maDH;
 @Nationalized   
    @Column(name = "ten_kh")
    private String tenKH;
   @Column(name = "ngay_ban")
    private Date ngayBan;
   @Column(name = "tong_tien")
    private double tongTien;

    public Order() {
    }

    public Order(String maDH, String tenKH, Date ngayBan, double tongTien) {
        this.maDH = maDH;
        this.tenKH = tenKH;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
   
}
