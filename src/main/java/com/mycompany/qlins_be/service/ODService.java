/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.OD;
import com.mycompany.qlins_be.entity.Order;
import com.mycompany.qlins_be.entity.Publisher;
import com.mycompany.qlins_be.model.ODDto;
import com.mycompany.qlins_be.repository.ODRepository;
import com.mycompany.qlins_be.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // Import cho @Transactional

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author Admin
 */
@Service
public class ODService {
     private final ODRepository odRepository;

     private final OrderRepository orderRepo;
    @Autowired
    public ODService(ODRepository odRepository,OrderRepository orderRepository) {
        this.odRepository = odRepository;
        this.orderRepo = orderRepository;
    }

    // Helper method to convert Entity to DTO
    private ODDto convertToDto(OD od) {
        ODDto dto = new ODDto();
        dto.setId(od.getId());
        dto.setTenSach(od.getTenSach());
        dto.setSoLuong(od.getSoLuong());
        dto.setDonGia(od.getDonGia());
        dto.setTongTien(od.getTongTien());
         if (od.getOrder()!= null) {
            dto.setMaDH(od.getOrder().getMaDH());
        }
        return dto;
    }

    // Helper method to convert DTO to Entity
    private OD convertToEntity(ODDto odDto) {
        OD od = new OD();
        
        if (odDto.getId() != null) {
            od.setId(odDto.getId());
        }

        od.setTenSach(odDto.getTenSach());
        od.setSoLuong(odDto.getSoLuong());
        od.setDonGia(odDto.getDonGia());
        od.setTongTien(odDto.getTongTien());
        if (odDto.getMaDH()!= null && !odDto.getMaDH().isEmpty()) {
            Order order = orderRepo.findById(odDto.getMaDH())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng với ID: " + odDto.getMaDH()));
            od.setOrder(order);
        }
        return od;
    }

    public List<ODDto> getAllODs() {
        return odRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ODDto> getODById(int id) { return odRepository.findById(id).map(this::convertToDto); }

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
        
        odRepository.deleteByOrder_MaDH(maDH);
    }

 public List<ODDto> getODsByMaDH(String maDH) {
        return odRepository.findByOrder_MaDH(maDH).stream() 
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
}
