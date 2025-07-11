/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;

/**
 * DTO (Data Transfer Object) cho đối tượng Book.
 * Được sử dụng để nhận dữ liệu từ client và trả về dữ liệu cho client.
 * Áp dụng các quy tắc validation cho dữ liệu đầu vào.
 */
@Data
public class BookDto {
    private String maSach; // Có thể null khi tạo mới, server sẽ sinh UUID

    @NotBlank(message = "Tên sách không được để trống!")
    private String tenSach;

    @Min(value = 0, message = "Số lượng phải là số không âm!")
    @NotNull(message = "Số lượng không được để trống!")
    private Integer soLuong; // Sử dụng Integer để có thể nhận null nếu client không gửi

    @Min(value = 0, message = "Giá bán phải là số không âm!")
    @NotNull(message = "Giá bán không được để trống!")
    private Double giaBan; // Sử dụng Double để có thể nhận null

    @NotBlank(message = "Mã tác giả không được để trống!")
    private String maTacGia; // Use author ID for creation/update
    private String tenTacGia; // Tên tác giả

     @NotBlank(message = "Mã nhà xuất bản không được để trống!")
    private String maNXB; 
    private String tenNXB; 

    private String duongDanAnh; // Đường dẫn ảnh (URL tương đối hoặc tên file)

    @Min(value = 1000, message = "Năm xuất bản không hợp lệ (phải sau năm 1000)!")
    @Max(value = 2100, message = "Năm xuất bản không hợp lệ (không thể quá năm 2100)!")
    @NotNull(message = "Năm xuất bản không được để trống!")
    private Integer namXB; // Sử dụng Integer để có thể nhận null

   @NotBlank(message = "Mã nhà danh mục không được để trống!")
    private String maDanhMuc; 
    private String tenDanhMuc; 


    
}

