/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;


import com.mycompany.qlins_be.model.OrderDto;
import com.mycompany.qlins_be.service.OrderService;
import jakarta.validation.Valid;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * REST Controller để quản lý các Đơn Hàng.
 * Sử dụng tầng Service để xử lý logic nghiệp vụ và DTO cho việc truyền tải dữ liệu.
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    private final OrderService orderService; // Inject OrderServiceImpl

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng với ID: " + id));
    }

     @PostMapping
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        OrderDto newOrder = orderService.addOrder(orderDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Xử lý trường hợp không tìm thấy đơn hàng hoặc lỗi khác từ service
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Tìm kiếm đơn hàng theo tên khách hàng.
     * @param query Chuỗi tìm kiếm (tên khách hàng).
     * @return Danh sách đơn hàng phù hợp.
     */
    @GetMapping("/search")
    public ResponseEntity<List<OrderDto>> searchOrders(@RequestParam String query) {
        List<OrderDto> orders = orderService.searchOrders(query);
        return ResponseEntity.ok(orders);
    }
}
