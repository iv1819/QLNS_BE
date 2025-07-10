/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Customer;
import com.mycompany.qlins_be.service.CustomerService;
import com.mycompany.qlins_be.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> list = customerService.getAllCustomers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDto>> searchCustomers(@RequestParam String keyword) {
        List<CustomerDto> list = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/next-id")
    public ResponseEntity<String> getNextCustomerId() {
        return ResponseEntity.ok(customerService.generateNextCustomerId());
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        try {
            CustomerDto saved = customerService.addCustomer(customerDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getReason());
            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @PutMapping("/{maKh}")
    public ResponseEntity<?> updateCustomer(@PathVariable String maKh, @Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        try {
            CustomerDto updated = customerService.updateCustomer(maKh, customerDto);
            return ResponseEntity.ok(updated);
        } catch (ResponseStatusException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getReason());
            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @DeleteMapping("/{maKh}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String maKh) {
        customerService.deleteCustomer(maKh);
        return ResponseEntity.ok().build();
    }
} 