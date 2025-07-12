package com.mycompany.qlins_be.model;

import jakarta.validation.constraints.NotBlank;

public class UpdateaccountDto {
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String matKhau;

    @NotBlank(message = "Trạng thái không được để trống!")
    private String trangThai;

    public @NotBlank(message = "Mật khẩu không được để trống!") String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(@NotBlank(message = "Mật khẩu không được để trống!") String matKhau) {
        this.matKhau = matKhau;
    }

    public @NotBlank(message = "Trạng thái không được để trống!") String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(@NotBlank(message = "Trạng thái không được để trống!") String trangThai) {
        this.trangThai = trangThai;
    }
}
