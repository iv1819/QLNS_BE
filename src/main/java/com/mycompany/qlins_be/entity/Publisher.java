/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.entity;

/**
 *
 * @author Admin
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "nha_xb")
public class Publisher {

    @Id 
     @UuidGenerator
    @Column(name = "ma_nxb")
    private String maNXB; // Mã nhà xuất bản
 @Nationalized   
@Column(name = "ten_nxb")
    private String tenNXB;
@Column(name = "sdt")
    private String sdt;    

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    public Publisher(String maNXB, String tenNXB, String sdt, String diaChi) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.sdt = sdt;
    }

    public Publisher() {
    }
}
