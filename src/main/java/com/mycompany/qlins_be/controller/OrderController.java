/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.OD;
import com.mycompany.qlins_be.entity.Order;
import com.mycompany.qlins_be.repository.ODRepository;
import com.mycompany.qlins_be.repository.OrderRepository;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@RestController // Đánh dấu đây là một REST Controller
@RequestMapping("/orders") 
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderbyID(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy don hang với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order) {
        Order neworder = orderRepository.save(order);
        return new ResponseEntity<>(neworder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy ctdh với ID: " + id);
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Tìm kiếm sách theo tên sách hoặc tên tác giả.
     * @param query Chuỗi tìm kiếm (có thể là tên sách hoặc tên tác giả).
     * @return Danh sách sách phù hợp.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrder(@RequestParam String query) {
        // Tìm kiếm theo tên sách hoặc tên tác giả (sử dụng LIKE %query%)
        return ResponseEntity.ok(orderRepository.findByTenKH(query));
    }
}
