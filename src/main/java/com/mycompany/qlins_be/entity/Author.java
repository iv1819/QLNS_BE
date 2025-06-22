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
@Table(name = "tac_gia", schema = "dbo")
public class Author {

    @Id
     @UuidGenerator
    @Column(name = "ma_tg")
    private String maTG; // Mã tác giả
 @Nationalized   
    @Column(name = "ten_tg")
    private String tenTG; // Tên tác giả

    public Author(String maTG, String tenTG) {
        this.maTG = maTG;
        this.tenTG = tenTG;
    }

    public Author() {
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

   
}