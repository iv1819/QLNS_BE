/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.Order;
import com.mycompany.qlins_be.model.OrderDto;
import com.mycompany.qlins_be.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class OrderService {
      private final OrderRepository orderRepository;
    private final ODService odService; // Inject ODService để quản lý chi tiết đơn hàng

    @Autowired
    public OrderService(OrderRepository orderRepository, ODService odService) {
        this.orderRepository = orderRepository;
        this.odService = odService;
    }

    // Helper method to convert Entity to DTO
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setMaDH(order.getMaDH());
        dto.setTenKH(order.getTenKH());
        dto.setNgayBan(order.getNgayBan() != null ? order.getNgayBan().toLocalDate() : null); // Convert java.sql.Date to LocalDate
        dto.setTongTien(order.getTongTien());
        dto.setTennv(order.getTennv());
        return dto;
    }

    // Helper method to convert DTO to Entity
    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        String madh = orderDto.getMaDH();
        String finalmaDh = (madh == null || madh.isEmpty()) ? null : madh;
        order.setMaDH(finalmaDh);
        order.setTenKH(orderDto.getTenKH());
        order.setNgayBan(orderDto.getNgayBan() != null ? Date.valueOf(orderDto.getNgayBan()) : null); // Convert LocalDate to java.sql.Date
        order.setTongTien(orderDto.getTongTien());
        order.setTennv(orderDto.getTennv());
        return order;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderDto> getOrderById(String id) {
        return orderRepository.findById(id).map(this::convertToDto);
    }

    public OrderDto addOrder(OrderDto orderDto) {
        // Sinh UUID cho maDH nếu chưa có
        if (orderDto.getMaDH() == null || orderDto.getMaDH().trim().isEmpty()) {
            orderDto.setMaDH("DH" +UUID.randomUUID().toString());
        }
        orderDto.setNgayBan(LocalDate.now());
        Order order = convertToEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    
    @Transactional // Đảm bảo cả hai thao tác (xóa OD và xóa Order) cùng thành công hoặc thất bại
    public void deleteOrder(String id) {
        // Kiểm tra xem đơn hàng có tồn tại không
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id " + id); // Hoặc throw ResponseStatusException
        }
        // Xóa tất cả chi tiết đơn hàng liên quan trước
        odService.deleteODByMaDH(id); // Gọi service để xóa ODs

        // Sau đó xóa đơn hàng
        orderRepository.deleteById(id);
    }

    public List<OrderDto> searchOrders(String query) {
        return orderRepository.findByTenKH(query).stream() // Giả sử tìm kiếm theo TenKH, bạn có thể thêm các tiêu chí khác
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
