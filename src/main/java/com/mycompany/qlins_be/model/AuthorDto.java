/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author trang
 */
@Data

public class AuthorDto {
        @NotBlank(message = "Mã tác giả không được để trống!")
    private String maTG;

    @NotBlank(message = "Tên tác giả không được để trống!")
    private String tenTG;

    public AuthorDto() {
    }

    public AuthorDto(String maTG, String tenTG) {
        this.maTG = maTG;
        this.tenTG = tenTG;
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
