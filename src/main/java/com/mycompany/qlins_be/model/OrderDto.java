/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate; // Sử dụng LocalDate cho ngày tháng hiện đại

@Data
public class OrderDto {
    private String maDH; // Mã đơn hàng (có thể null khi tạo mới, backend sinh ra)

    private String tenKH;
    private LocalDate ngayBan;
    @NotNull(message = "Tổng tiền không được để trống!")
    @Min(value = 0, message = "Tổng tiền phải là số không âm!")
    private Double tongTien;
    @NotBlank(message = "Tên nhân viên không được để trống!")
    private String tennv;
}