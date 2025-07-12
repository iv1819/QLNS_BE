package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.Employee;
import com.mycompany.qlins_be.model.EmployeeDto;
import com.mycompany.qlins_be.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Lấy tất cả nhân viên
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Lấy nhân viên theo ID
    public EmployeeDto getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id));
    }

    // Thêm nhân viên mới
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getMaNv() == null || employeeDto.getMaNv().isEmpty()) {
            // Sử dụng mã tự động thay vì UUID
            employeeDto.setMaNv(generateAutoEmployeeId());
        } else {
            if (employeeRepository.existsById(employeeDto.getMaNv())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã nhân viên đã tồn tại: " + employeeDto.getMaNv());
            }
        }
        // Kiểm tra số điện thoại đủ 10 số
        if (employeeDto.getSdt() == null || !employeeDto.getSdt().matches("^\\d{10}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại phải là số và có đúng 10 chữ số!");
        }
        // Kiểm tra trùng số điện thoại
        if (employeeRepository.existsBySdt(employeeDto.getSdt())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại đã tồn tại!");
        }
        // Kiểm tra lương > 0
        if (employeeDto.getLuong() == null || employeeDto.getLuong().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lương phải lớn hơn 0!");
        }
        Employee employee = convertToEntity(employeeDto);
        Employee newEmployee = employeeRepository.save(employee);
        return convertToDto(newEmployee);
    }

    // Cập nhật nhân viên
    public EmployeeDto updateEmployee(String id, EmployeeDto employeeDetailsDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id));

        // Kiểm tra số điện thoại đủ 10 số
        if (employeeDetailsDto.getSdt() == null || !employeeDetailsDto.getSdt().matches("^\\d{10}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại phải là số và có đúng 10 chữ số!");
        }
        // Kiểm tra trùng số điện thoại (trừ trường hợp giữ nguyên số cũ)
        if (!existingEmployee.getSdt().equals(employeeDetailsDto.getSdt()) && employeeRepository.existsBySdt(employeeDetailsDto.getSdt())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại đã tồn tại!");
        }
        // Kiểm tra lương > 0
        if (employeeDetailsDto.getLuong() == null || employeeDetailsDto.getLuong().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lương phải lớn hơn 0!");
        }
        // Cập nhật các trường
        existingEmployee.setTenNv(employeeDetailsDto.getTenNv());
        existingEmployee.setNgaySinh(employeeDetailsDto.getNgaySinh());
        existingEmployee.setNgayVaoLam(employeeDetailsDto.getNgayVaoLam());
        existingEmployee.setSdt(employeeDetailsDto.getSdt());
        existingEmployee.setLuong(employeeDetailsDto.getLuong());
        existingEmployee.setTenCv(employeeDetailsDto.getTenCv());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDto(updatedEmployee);
    }

    // Xóa nhân viên
    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // Tìm kiếm nhân viên theo tên hoặc số điện thoại
    public List<EmployeeDto> searchEmployees(String query) {
        return employeeRepository.findByTenNvContainingIgnoreCaseOrSdtContaining(query, query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Tìm kiếm theo tên
    public List<EmployeeDto> searchEmployeesByName(String name) {
        return employeeRepository.findByTenNvContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Tìm kiếm theo số điện thoại
    public List<EmployeeDto> searchEmployeesByPhone(String phone) {
        return employeeRepository.findBySdtContaining(phone).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    // Sinh mã nhân viên tự động theo format NVxxx
    public String generateAutoEmployeeId() {
        try {
            String sql = "SELECT TOP 1 ma_nv FROM nhanvien WHERE ma_nv LIKE 'NV%' ORDER BY CAST(SUBSTRING(ma_nv, 3, LEN(ma_nv)-2) AS INT) DESC";
            String maNVCuoi = jdbcTemplate.query(sql, rs -> rs.next() ? rs.getString("ma_nv") : null);
            if (maNVCuoi != null && maNVCuoi.length() >= 3) {
                String soCuoi = maNVCuoi.substring(2);
                try {
                    int soMoi = Integer.parseInt(soCuoi) + 1;
                    return String.format("NV%03d", soMoi);
                } catch (NumberFormatException e) {
                    return "NV001";
                }
            } else {
                return "NV001";
            }
        } catch (Exception e) {
            return "NV001";
        }
    }

    // Lấy danh sách tên công việc phân biệt
    public List<String> getAllDistinctTenCv() {
        return employeeRepository.findAllDistinctTenCv();
    }

    // Chuyển đổi Entity sang DTO
    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setMaNv(employee.getMaNv());
        dto.setTenNv(employee.getTenNv());
        dto.setNgaySinh(employee.getNgaySinh());
        dto.setNgayVaoLam(employee.getNgayVaoLam());
        dto.setSdt(employee.getSdt());
        dto.setLuong(employee.getLuong());
        dto.setTenCv(employee.getTenCv());
        return dto;
    }

    // Chuyển đổi DTO sang Entity
    private Employee convertToEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setMaNv(dto.getMaNv());
        employee.setTenNv(dto.getTenNv());
        employee.setNgaySinh(dto.getNgaySinh());
        employee.setNgayVaoLam(dto.getNgayVaoLam());
        employee.setSdt(dto.getSdt());
        employee.setLuong(dto.getLuong());
        employee.setTenCv(dto.getTenCv());
        return employee;
    }
} 