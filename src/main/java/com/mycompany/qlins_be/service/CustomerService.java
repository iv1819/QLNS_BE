package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.Customer;
import com.mycompany.qlins_be.model.CustomerDto;
import com.mycompany.qlins_be.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(String maKh) {
        Customer c = customerRepository.findById(maKh)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng!"));
        return toDto(c);
    }

    public CustomerDto addCustomer(CustomerDto dto) {
        validatePhone(dto.getSdt(), null);
        Customer entity = toEntity(dto);
        entity.setMaKh(generateNextCustomerId());
        Customer saved = customerRepository.save(entity);
        return toDto(saved);
    }

    public CustomerDto updateCustomer(String maKh, CustomerDto dto) {
        Customer existing = customerRepository.findById(maKh)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng!"));
        validatePhone(dto.getSdt(), maKh);
        existing.setTenKh(dto.getTenKh());
        existing.setSdt(dto.getSdt());
        Customer saved = customerRepository.save(existing);
        return toDto(saved);
    }

    public void deleteCustomer(String maKh) {
        customerRepository.deleteById(maKh);
    }

    public List<CustomerDto> searchCustomers(String keyword) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getMaKh().toLowerCase().contains(keyword.toLowerCase())
                        || c.getTenKh().toLowerCase().contains(keyword.toLowerCase())
                        || c.getSdt().contains(keyword))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public String generateNextCustomerId() {
        try {
            String sql = "SELECT TOP 1 ma_kh FROM khachhang WHERE ma_kh LIKE 'KH%' ORDER BY CAST(SUBSTRING(ma_kh, 3, LEN(ma_kh)-2) AS INT) DESC";
            String maKHCuoi = jdbcTemplate.query(sql, rs -> rs.next() ? rs.getString("ma_kh") : null);
            if (maKHCuoi != null && maKHCuoi.length() >= 3) {
                String soCuoi = maKHCuoi.substring(2);
                try {
                    int soMoi = Integer.parseInt(soCuoi) + 1;
                    return String.format("KH%03d", soMoi);
                } catch (NumberFormatException e) {
                    return "KH001";
                }
            } else {
                return "KH001";
            }
        } catch (Exception e) {
            return "KH001";
        }
    }

    private void validatePhone(String sdt, String currentMaKh) {
        if (sdt == null || !sdt.matches("\\d{10}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại phải đủ 10 số!");
        }
        Customer existing = customerRepository.findBySdt(sdt);
        if (existing != null && (currentMaKh == null || !existing.getMaKh().equals(currentMaKh))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại đã tồn tại!");
        }
    }

    private CustomerDto toDto(Customer c) {
        return new CustomerDto(c.getMaKh(), c.getTenKh(), c.getSdt());
    }
    private Customer toEntity(CustomerDto dto) {
        return new Customer(dto.getMaKh(), dto.getTenKh(), dto.getSdt());
    }
} 