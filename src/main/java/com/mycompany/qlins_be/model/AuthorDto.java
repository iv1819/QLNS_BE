package com.mycompany.qlins_be.model;

import jakarta.validation.constraints.NotBlank;

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

