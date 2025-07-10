/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.OD;
import com.mycompany.qlins_be.model.ODDto;
import com.mycompany.qlins_be.repository.ODRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // Import cho @Transactional

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
/**
 *
 * @author Admin
 */
@Service
public class ODService {
     private final ODRepository odRepository;

    @Autowired
    public ODService(ODRepository odRepository) {
        this.odRepository = odRepository;
    }

    // Helper method to convert Entity to DTO
    private ODDto convertToDto(OD od) {
        ODDto dto = new ODDto();
        dto.setId(od.getId());
        dto.setTenSach(od.getTenSach());
        dto.setSoLuong(od.getSoLuong());
        dto.setDonGia(od.getDonGia());
        dto.setTongTien(od.getTongTien());
        dto.setMaDH(od.getMaDH());
        return dto;
    }

    // Helper method to convert DTO to Entity
    private OD convertToEntity(ODDto odDto) {
        OD od = new OD();
        od.setId(odDto.getId() != null ? odDto.getId() : 0); // ID có thể null khi thêm mới
        od.setTenSach(odDto.getTenSach());
        od.setSoLuong(odDto.getSoLuong());
        od.setDonGia(odDto.getDonGia());
        od.setTongTien(odDto.getTongTien());
        od.setMaDH(odDto.getMaDH());
        return od;
    }

    public List<ODDto> getAllODs() {
        return odRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ODDto> getODById(int id) {
        return odRepository.findById(id).map(this::convertToDto);
    }

    public ODDto addOD(ODDto odDto) {
        OD od = convertToEntity(odDto);
        // ID sẽ tự động được sinh bởi CSDL nếu là GenerationType.IDENTITY
        OD savedOD = odRepository.save(od);
        return convertToDto(savedOD);
    }

    public void deleteOD(int id) {
        if (!odRepository.existsById(id)) {
            throw new RuntimeException("OD not found with id " + id); // Hoặc throw ResponseStatusException
        }
        odRepository.deleteById(id);
    }

    @Transactional // Đảm bảo toàn bộ quá trình xóa thành công hoặc thất bại
    public void deleteODByMaDH(String maDH) {
        // Có thể thêm kiểm tra tồn tại trước khi xóa nếu cần
        if (!odRepository.existsByMaDH(maDH)) {
            // Không ném lỗi nếu không tìm thấy, chỉ đơn giản là không có gì để xóa
            return;
        }
        odRepository.deleteByMaDH(maDH);
    }

    public List<ODDto> getODsByMaDH(String maDH) {
        return odRepository.findByMaDH(maDH).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ODDto> searchODs(String query) {
        // Cần định nghĩa phương thức tìm kiếm trong ODRepository nếu muốn tìm kiếm chi tiết đơn hàng
        // Ví dụ: odRepository.findByTenSachContainingIgnoreCase(query)
        // Hiện tại không có phương thức này trong ODRepository nên sẽ trả về tất cả hoặc danh sách rỗng
        return odRepository.findAll().stream()
                .filter(od -> od.getTenSach().toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(od.getMaDH()).contains(query)) // Ví dụ tìm kiếm theo tên sách hoặc mã đơn hàng
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
