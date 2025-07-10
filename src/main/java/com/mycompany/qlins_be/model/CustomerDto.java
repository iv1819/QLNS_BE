package com.mycompany.qlins_be.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class CustomerDto {
    private String maKh;

    @NotBlank(message = "Tên khách hàng không được để trống!")
    private String tenKh;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có đúng 10 chữ số!")
    private String sdt;

    public CustomerDto() {}
    public CustomerDto(String maKh, String tenKh, String sdt) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sdt = sdt;
    }
} 