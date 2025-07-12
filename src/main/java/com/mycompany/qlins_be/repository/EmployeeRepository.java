package com.mycompany.qlins_be.repository;

import com.mycompany.qlins_be.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    // Tìm kiếm nhân viên theo tên (không phân biệt hoa thường)
    List<Employee> findByTenNvContainingIgnoreCase(String tenNv);
    
    // Tìm kiếm nhân viên theo số điện thoại
    List<Employee> findBySdtContaining(String sdt);
    
    // Tìm kiếm nhân viên theo tên hoặc số điện thoại
    List<Employee> findByTenNvContainingIgnoreCaseOrSdtContaining(String tenNv, String sdt);
    
    // Tìm kiếm nhân viên theo năm vào làm (sử dụng @Query)
    @Query("SELECT e FROM Employee e WHERE YEAR(e.ngayVaoLam) = :year")
    List<Employee> findByNgayVaoLamYear(@Param("year") Integer year);

    // Kiểm tra trùng lặp tên (không phân biệt hoa thường)
    boolean existsByTenNvIgnoreCase(String tenNv);

    // Kiểm tra trùng số điện thoại
    boolean existsBySdt(String sdt);

    // Tìm nhân viên theo tên (không phân biệt hoa thường) để kiểm tra khi cập nhật
    Optional<Employee> findByTenNvIgnoreCase(String tenNv);

    @Query("SELECT DISTINCT e.tenCv FROM Employee e")
    List<String> findAllDistinctTenCv();
} 