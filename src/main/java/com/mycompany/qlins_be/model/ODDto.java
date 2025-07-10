/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ODDto {
    private Integer id; // ID của chi tiết đơn hàng (có thể null khi tạo mới, backend tự sinh)

    @NotBlank(message = "Tên sách không được để trống!")
    private String tenSach;

    @NotNull(message = "Số lượng không được để trống!")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0!")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được để trống!")
    @Min(value = 0, message = "Đơn giá phải là số không âm!")
    private Double donGia;

    @NotNull(message = "Tổng tiền chi tiết không được để trống!")
    @Min(value = 0, message = "Tổng tiền chi tiết phải là số không âm!")
    private Double tongTien;

    @NotBlank(message = "Mã đơn hàng không được để trống!")
    private String maDH; // Mã đơn hàng mà chi tiết này thuộc về
}
