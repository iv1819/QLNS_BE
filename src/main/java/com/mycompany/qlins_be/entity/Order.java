/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
@Nationalized   
    @Column(name = "ten_nv")
    private String tennv;
    public Order() {
    }
 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OD> ods = new ArrayList<>();

    public List<OD> getOds() {
        return ods;
    }
    public Order(String maDH, String tenKH, Date ngayBan, double tongTien, String tennv) {
        this.maDH = maDH;
        this.tenKH = tenKH;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.tennv = tennv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
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
