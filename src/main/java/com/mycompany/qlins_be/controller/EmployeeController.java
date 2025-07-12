package com.mycompany.qlins_be.controller;

import com.mycompany.qlins_be.model.EmployeeDto;
import com.mycompany.qlins_be.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // GET /employees - Lấy tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // GET /employees/{id} - Lấy nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    // POST /employees - Thêm nhân viên mới
    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // PUT /employees/{id} - Cập nhật nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @Valid @RequestBody EmployeeDto employeeDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE /employees/{id} - Xóa nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // GET /employees/search?query=... - Tìm kiếm nhân viên
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(@RequestParam String query) {
        List<EmployeeDto> employees = employeeService.searchEmployees(query);
        return ResponseEntity.ok(employees);
    }

    // GET /employees/search/name?name=... - Tìm kiếm theo tên
    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeDto>> searchEmployeesByName(@RequestParam String name) {
        List<EmployeeDto> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    // GET /employees/search/phone?phone=... - Tìm kiếm theo số điện thoại
    @GetMapping("/search/phone")
    public ResponseEntity<List<EmployeeDto>> searchEmployeesByPhone(@RequestParam String phone) {
        List<EmployeeDto> employees = employeeService.searchEmployeesByPhone(phone);
        return ResponseEntity.ok(employees);
    }



    // GET /employees/auto-id - Lấy mã nhân viên tự động
    @GetMapping("/auto-id")
    public ResponseEntity<String> getAutoEmployeeId() {
        String autoId = employeeService.generateAutoEmployeeId();
        return ResponseEntity.ok(autoId);
    }

    // GET /employees/positions - Lấy danh sách tên công việc (chức vụ)
    @GetMapping("/positions")
    public ResponseEntity<List<String>> getAllPositions() {
        return ResponseEntity.ok(employeeService.getAllDistinctTenCv());
    }
} 