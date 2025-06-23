package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.entity.Employee;
import com.mycompany.qlins_be.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET /api/employees - Lấy tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    // GET /api/employees/{id} - Lấy nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id));
    }

    // POST /employees - Thêm nhân viên mới
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        // Tạo mã nhân viên mới nếu không có
        if (employee.getMaNv() == null || employee.getMaNv().trim().isEmpty()) {
            employee.setMaNv(UUID.randomUUID().toString());
        }

        // Kiểm tra trùng mã nhân viên
        if (employeeRepository.existsById(employee.getMaNv())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã nhân viên '" + employee.getMaNv() + "' đã tồn tại.");
        }
        // Kiểm tra trùng tên nhân viên
        if (employeeRepository.existsByTenNvIgnoreCase(employee.getTenNv())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên nhân viên '" + employee.getTenNv() + "' đã tồn tại.");
        }

        Employee newEmployee = employeeRepository.save(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    // PUT /employees/{id} - Cập nhật nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @Valid @RequestBody Employee employeeDetails) {
        // Tìm nhân viên hiện có
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id));

        // Kiểm tra xem tên có thay đổi không và nếu có thì có bị trùng không
        if (!existingEmployee.getTenNv().equalsIgnoreCase(employeeDetails.getTenNv())) {
            Optional<Employee> employeeWithSameName = employeeRepository.findByTenNvIgnoreCase(employeeDetails.getTenNv());
            if (employeeWithSameName.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên nhân viên '" + employeeDetails.getTenNv() + "' đã tồn tại.");
            }
        }

        // Cập nhật các trường
        existingEmployee.setTenNv(employeeDetails.getTenNv());
        existingEmployee.setNgaySinh(employeeDetails.getNgaySinh());
        existingEmployee.setNgayVaoLam(employeeDetails.getNgayVaoLam());
        existingEmployee.setSdt(employeeDetails.getSdt());
        existingEmployee.setLuong(employeeDetails.getLuong());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE /employees/{id} - Xóa nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với ID: " + id);
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/employees/search?query=... - Tìm kiếm nhân viên
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String query) {
        // Tìm kiếm theo tên nhân viên hoặc số điện thoại
        List<Employee> employees = employeeRepository.findByTenNvContainingIgnoreCaseOrSdtContaining(query, query);
        return ResponseEntity.ok(employees);
    }

    // GET /api/employees/search/name?name=... - Tìm kiếm theo tên
    @GetMapping("/search/name")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        List<Employee> employees = employeeRepository.findByTenNvContainingIgnoreCase(name);
        return ResponseEntity.ok(employees);
    }

    // GET /api/employees/search/phone?phone=... - Tìm kiếm theo số điện thoại
    @GetMapping("/search/phone")
    public ResponseEntity<List<Employee>> searchEmployeesByPhone(@RequestParam String phone) {
        List<Employee> employees = employeeRepository.findBySdtContaining(phone);
        return ResponseEntity.ok(employees);
    }

    // GET /api/employees/search/salary?min=...&max=... - Tìm kiếm theo khoảng lương
    @GetMapping("/search/salary")
    public ResponseEntity<List<Employee>> searchEmployeesBySalaryRange(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        List<Employee> employees = employeeRepository.findByLuongBetween(min, max);
        return ResponseEntity.ok(employees);
    }
} 