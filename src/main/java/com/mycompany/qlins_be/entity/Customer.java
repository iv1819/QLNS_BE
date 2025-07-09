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
    @Column(name = "ma_kh")
    private String maKh;
    
    @Column(name = "ten_kh")
    private String tenKh;
    
    @Column(name = "sdt")
    private String sdt;
    
    public Customer() {}

    public Customer(String maKh, String tenKh, String sdt) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sdt = sdt;
    }

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

    @Override
    public String toString() {
        return "Customer{" +
                "maKh='" + maKh + '\'' +
                ", tenKh='" + tenKh + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
} 