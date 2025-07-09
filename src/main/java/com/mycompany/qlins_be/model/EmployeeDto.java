/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.model;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) cho đối tượng Employee.
 * Được sử dụng để nhận dữ liệu từ client và trả về dữ liệu cho client.
 * Áp dụng các quy tắc validation cho dữ liệu đầu vào.
 */
@Data
public class EmployeeDto {
    private String maNv; // Có thể null khi tạo mới, server sẽ sinh UUID

    @NotBlank(message = "Tên nhân viên không được để trống!")
    private String tenNv;

    @NotNull(message = "Ngày sinh không được để trống!")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    private LocalDate ngaySinh;

    @NotNull(message = "Ngày vào làm không được để trống!")
    @Past(message = "Ngày vào làm phải là ngày trong quá khứ hoặc hiện tại!")
    private LocalDate ngayVaoLam;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có đúng 10 chữ số!")
    private String sdt;

    @NotNull(message = "Lương không được để trống!")
    @Min(value = 0, message = "Lương phải là số không âm!")
    private BigDecimal luong;

    @NotBlank(message = "Tên công việc không được để trống!")
    private String tenCv;

    // Constructor mặc định
    public EmployeeDto() {
    }

    // Constructor với tham số
    public EmployeeDto(String maNv, String tenNv, LocalDate ngaySinh, LocalDate ngayVaoLam, String sdt, BigDecimal luong, String tenCv) {
        this.maNv = maNv;
        this.tenNv = tenNv;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
        this.sdt = sdt;
        this.luong = luong;
        this.tenCv = tenCv;
    }

    // Getter và Setter methods
    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public BigDecimal getLuong() {
        return luong;
    }

    public void setLuong(BigDecimal luong) {
        this.luong = luong;
    }

    public String getTenCv() {
        return tenCv;
    }
    public void setTenCv(String tenCv) {
        this.tenCv = tenCv;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "maNv='" + maNv + '\'' +
                ", tenNv='" + tenNv + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", ngayVaoLam=" + ngayVaoLam +
                ", sdt='" + sdt + '\'' +
                ", luong=" + luong +
                ", tenCv='" + tenCv + '\'' +
                '}';
    }
} 