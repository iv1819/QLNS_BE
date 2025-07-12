# Tài Liệu Hệ Thống QLNS (Quản Lý Nhà Sách)

## 📋 Mục Lục

1. [Cấu Trúc Backend và Frontend](#1-cấu-trúc-backend-và-frontend)
2. [Lý Thuyết Cơ Bản về Cấu Trúc](#2-lý-thuyết-cơ-bản-về-cấu-trúc)
3. [Luồng Hoạt Động Chi Tiết - Quản Lý Nhân Viên & Khách Hàng](#3-luồng-hoạt-động-chi-tiết---quản-lý-nhân-viên--khách-hàng)
4. [Chi Tiết Lý Thuyết về API](#4-chi-tiết-lý-thuyết-về-api)
5. [Công Nghệ Sử Dụng & Lý Thuyết](#5-công-nghệ-sử-dụng--lý-thuyết)

---

## 1. Cấu Trúc Backend và Frontend

### 🔙 Backend Structure (QLNS_BE)

```
QLNS_BE/
├── src/main/java/com/mycompany/qlins_be/
│   ├── controller/           # REST API Controllers
│   │   ├── AccountController.java      # Quản lý tài khoản đăng nhập
│   │   ├── CustomerController.java     # Quản lý khách hàng
│   │   ├── EmployeeController.java     # Quản lý nhân viên
│   │   ├── BookController.java         # Quản lý sách
│   │   ├── AuthorController.java       # Quản lý tác giả
│   │   ├── CategoryController.java     # Quản lý danh mục
│   │   ├── PublisherController.java    # Quản lý nhà xuất bản
│   │   ├── OrderController.java        # Quản lý đơn hàng
│   │   ├── ODController.java          # Quản lý chi tiết đơn hàng
│   │   └── UploadController.java      # Upload file/hình ảnh
│   │
│   ├── service/              # Business Logic Layer
│   │   ├── CustomerService.java
│   │   ├── EmployeeService.java
│   │   ├── BookService.java
│   │   ├── AuthorService.java
│   │   ├── AccountService.java
│   │   └── ...
│   │
│   ├── repository/           # Data Access Layer
│   │   ├── CustomerRepository.java
│   │   ├── EmployeeRepository.java
│   │   ├── BookRepository.java
│   │   └── ...
│   │
│   ├── entity/              # JPA Entities (Database Models)
│   │   ├── Customer.java
│   │   ├── Employee.java
│   │   ├── Book.java
│   │   ├── Author.java
│   │   ├── Category.java
│   │   └── ...
│   │
│   ├── model/               # Data Transfer Objects (DTOs)
│   │   ├── CustomerDto.java
│   │   ├── EmployeeDto.java
│   │   ├── BookDto.java
│   │   └── ...
│   │
│   └── QlinsBeApplication.java  # Main Spring Boot Application
│
├── src/main/resources/
│   ├── application.yml       # Cấu hình ứng dụng
│   └── static/              # Static files
│
├── target/                  # Compiled files
├── uploads/                 # Uploaded images storage
└── pom.xml                 # Maven dependencies
```

### 🔚 Frontend Structure (QLNS_FE)

```
QLNS_FE/
├── src/main/java/
│   ├── View/                # Giao diện người dùng (Java Swing)
│   │   ├── Login.java              # Màn hình đăng nhập
│   │   ├── MainMenu_Manager2.java  # Menu chính cho quản lý
│   │   ├── CustomerM.java          # Quản lý khách hàng
│   │   ├── EmployeeM.java          # Quản lý nhân viên
│   │   ├── BookM.java              # Quản lý sách
│   │   └── ...
│   │
│   ├── Presenter/           # MVP Pattern - Business Logic
│   │   ├── MainMenuPresenter.java
│   │   ├── MainMenuManagerPresenter.java
│   │   ├── CustomerPresenter.java
│   │   └── ...
│   │
│   ├── Model/               # Data Models
│   │   ├── Customer.java
│   │   ├── Employee.java
│   │   ├── Book.java
│   │   └── ...
│   │
│   ├── API/                 # HTTP Client for Backend Communication
│   │   ├── ApiClientBase.java
│   │   ├── CustomerApiClient.java
│   │   ├── EmployeeApiClient.java
│   │   ├── BookApiClient.java
│   │   └── ...
│   │
│   ├── util/                # Utility classes
│   │   └── ...
│   │
│   ├── images/              # GUI Resources
│   │   └── ...
│   │
│   └── com/mycompany/qlins/
│       └── QliNS.java       # Main entry point
│
├── target/                  # Compiled files
├── TimingFramework-0.55.jar # Animation library
└── pom.xml                 # Maven dependencies
```

### 🗄️ Database Structure

```
Database: QLNS_API (SQL Server)
├── Tables:
│   ├── Customer (KhachHang)
│   ├── Employee (NhanVien)
│   ├── Book (Sach)
│   ├── Author (TacGia)
│   ├── Category (DanhMuc)
│   ├── Publisher (NhaXuatBan)
│   ├── Order (DonHang)
│   ├── OrderDetail (ChiTietDonHang)
│   └── Account (TaiKhoan)
```

---

## 2. Lý Thuyết Cơ Bản về Cấu Trúc

### 🏗️ Kiến Trúc Tổng Thể

#### **2.1 Kiến Trúc 3-Layer (3-Tier Architecture)**

Hệ thống QLNS sử dụng kiến trúc 3 tầng cổ điển:

**🎨 Presentation Layer (Tầng Giao Diện)**
- **Frontend**: Java Swing Desktop Application
- **Chức năng**: Hiển thị giao diện, nhận input từ user, hiển thị kết quả
- **Thành phần**: View classes, Event handlers, Form validation

**🧠 Business Logic Layer (Tầng Logic Nghiệp Vụ)**
- **Backend**: Spring Boot REST API
- **Chức năng**: Xử lý logic nghiệp vụ, validation, business rules
- **Thành phần**: Service classes, Controllers, DTOs

**🗄️ Data Access Layer (Tầng Truy Cập Dữ Liệu)**
- **Backend**: JPA/Hibernate + SQL Server
- **Chức năng**: Quản lý database operations, data persistence
- **Thành phần**: Repository interfaces, Entity classes, Database

#### **2.2 Design Patterns Sử Dụng**

**🎯 MVP Pattern (Model-View-Presenter) - Frontend**
```
View ↔ Presenter ↔ Model
```
- **View**: Giao diện người dùng (Java Swing)
- **Presenter**: Logic xử lý, điều khiển luồng dữ liệu
- **Model**: Dữ liệu và business objects

**🔄 MVC Pattern (Model-View-Controller) - Backend**
```
Client → Controller → Service → Repository → Database
```
- **Controller**: Nhận HTTP requests, trả về responses
- **Service**: Business logic và validation
- **Repository**: Data access operations

**🏭 Repository Pattern**
- Abstraction layer giữa business logic và data access
- Cung cấp interface thống nhất cho database operations
- Dễ dàng testing và mocking

**📦 DTO Pattern (Data Transfer Object)**
- Truyền tải dữ liệu giữa layers
- Giảm thiểu số lần gọi remote calls
- Validation và serialization

#### **2.3 RESTful API Architecture**

**📡 REST Principles:**
1. **Stateless**: Mỗi request độc lập
2. **Resource-based**: URLs đại diện cho resources
3. **HTTP Methods**: GET, POST, PUT, DELETE
4. **JSON Format**: Data exchange format
5. **HTTP Status Codes**: Meaningful response codes

**🔗 API Structure:**
```
Base URL: http://localhost:8080/api
Resource: /customers
Operations:
- GET /customers          → Lấy tất cả
- GET /customers/{id}     → Lấy theo ID
- POST /customers         → Thêm mới
- PUT /customers/{id}     → Cập nhật
- DELETE /customers/{id}  → Xóa
```

#### **2.4 Database Design Principles**

**🔑 Normalization:**
- 1NF: Atomic values, unique rows
- 2NF: No partial dependencies
- 3NF: No transitive dependencies

**🔗 Relationships:**
- One-to-Many: Customer → Orders
- Many-to-One: Books → Category
- Many-to-Many: Books ← OrderDetails → Orders

**🛡️ Constraints:**
- Primary Keys: Unique identifiers
- Foreign Keys: Referential integrity
- Check Constraints: Data validation
- Unique Constraints: Prevent duplicates

---

## 3. Luồng Hoạt Động Chi Tiết - Quản Lý Nhân Viên & Khách Hàng

### 👥 Luồng Quản Lý Khách Hàng

#### **3.1 Khởi Tạo Hệ Thống**

**🚀 Frontend Startup:**
```java
// QliNS.java - Main Entry Point
public static void main(String[] args) {
    // 1. Khởi tạo Swing Application
    java.awt.EventQueue.invokeLater(() -> {
        // 2. Tạo và hiển thị Login form
        Login lg = new Login();
        lg.setLocationRelativeTo(null);
        lg.setVisible(true);
    });
}
```

**🔐 Authentication Flow:**
```java
// Login.java - User Authentication
private void loginButtonActionPerformed() {
    // 1. Lấy username/password từ UI
    String username = txtUsername.getText();
    String password = txtPassword.getText();
    
    // 2. Gọi API login
    AccountApiClient apiClient = new AccountApiClient();
    Map<String, Object> response = apiClient.login(username, password);
    
    // 3. Xử lý kết quả
    if ((Boolean) response.get("success")) {
        String role = (String) response.get("role");
        // 4. Chuyển đến main menu tương ứng
        if ("Quản lí".equals(role)) {
            showManagerMenu();
        } else {
            showEmployeeMenu();
        }
    }
}
```

#### **3.2 Mở Màn Hình Quản Lý Khách Hàng**

**🎯 Navigation Flow:**
```java
// MainMenu_Manager2.java - Manager Menu
private void btnCustomerActionPerformed() {
    // 1. Tạo CustomerM view
    CustomerM customerView = new CustomerM(mainMenuManagerPresenter);
    
    // 2. Ẩn menu hiện tại
    this.setVisible(false);
    
    // 3. Hiển thị customer management
    customerView.setVisible(true);
    customerView.setLocationRelativeTo(null);
}
```

#### **3.3 Load Dữ Liệu Khách Hàng**

**📊 Data Loading Process:**

**Frontend (CustomerM.java):**
```java
public CustomerM(MainMenuManagerPresenter mainMenuManagerPresenter) {
    // 1. Khởi tạo components
    initComponents();
    
    // 2. Khởi tạo API client
    this.customerApiClient = new CustomerApiClient();
    
    // 3. Load dữ liệu ban đầu
    loadCustomerData();
    
    // 4. Thiết lập event listeners
    setupEventListeners();
}

private void loadCustomerData() {
    try {
        // 1. Gọi API lấy danh sách khách hàng
        List<Customer> customers = customerApiClient.getAll();
        
        // 2. Cập nhật table model
        updateTableModel(customers);
        
        // 3. Load mã khách hàng tiếp theo
        String nextId = customerApiClient.getNextId();
        txtMaKH.setText(nextId);
        
    } catch (IOException e) {
        showErrorMessage("Lỗi khi tải dữ liệu: " + e.getMessage());
    }
}
```

**Backend API Call:**
```java
// CustomerApiClient.java - HTTP Client
public List<Customer> getAll() throws IOException {
    // 1. Tạo HTTP request
    Request request = new Request.Builder()
        .url("http://localhost:8080/api/customers")
        .build();
    
    // 2. Thực hiện request
    try (Response response = client.newCall(request).execute()) {
        // 3. Parse JSON response
        return mapper.readValue(response.body().string(), 
               new TypeReference<List<Customer>>(){});
    }
}
```

**Backend Controller:**
```java
// CustomerController.java - REST Endpoint
@GetMapping
public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    // 1. Gọi service layer
    List<CustomerDto> customers = customerService.getAllCustomers();
    
    // 2. Trả về response
    return ResponseEntity.ok(customers);
}
```

**Backend Service:**
```java
// CustomerService.java - Business Logic
public List<CustomerDto> getAllCustomers() {
    // 1. Gọi repository lấy entities
    List<Customer> entities = customerRepository.findAll();
    
    // 2. Convert entities to DTOs
    return entities.stream()
                  .map(this::toDto)
                  .collect(Collectors.toList());
}

private CustomerDto toDto(Customer entity) {
    // Entity to DTO mapping
    return CustomerDto.builder()
                     .maKh(entity.getMaKh())
                     .tenKh(entity.getTenKh())
                     .sdt(entity.getSdt())
                     .build();
}
```

**Backend Repository:**
```java
// CustomerRepository.java - Data Access
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // JPA automatically provides:
    // - findAll()
    // - findById(String id)
    // - save(Customer customer)
    // - deleteById(String id)
    
    // Custom queries
    List<Customer> findByTenKhContainingIgnoreCase(String tenKh);
    Optional<Customer> findBySdt(String sdt);
}
```

#### **3.4 Thêm Khách Hàng Mới**

**📝 Add Customer Flow:**

**Frontend Input Validation:**
```java
// CustomerM.java - Add Button Handler
private void btnAddActionPerformed() {
    // 1. Validate input fields
    if (!validateInput()) {
        return;
    }
    
    // 2. Tạo Customer object
    Customer customer = new Customer();
    customer.setTenKh(txtTenKH.getText().trim());
    customer.setSdt(txtSDT.getText().trim());
    
    try {
        // 3. Gọi API thêm customer
        Customer savedCustomer = customerApiClient.add(customer);
        
        // 4. Refresh table
        loadCustomerData();
        
        // 5. Clear form
        clearForm();
        
        // 6. Show success message
        showSuccessMessage("Thêm khách hàng thành công!");
        
    } catch (IOException e) {
        // 7. Handle errors
        handleApiError(e);
    }
}

private boolean validateInput() {
    // Client-side validation
    if (txtTenKH.getText().trim().isEmpty()) {
        showError("Tên khách hàng không được trống!");
        return false;
    }
    
    if (txtSDT.getText().trim().isEmpty()) {
        showError("Số điện thoại không được trống!");
        return false;
    }
    
    // Phone number format validation
    if (!isValidPhoneNumber(txtSDT.getText().trim())) {
        showError("Số điện thoại không hợp lệ!");
        return false;
    }
    
    return true;
}
```

**Backend Processing:**
```java
// CustomerController.java - POST Endpoint
@PostMapping
public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customerDto, 
                                   BindingResult bindingResult) {
    // 1. Server-side validation
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
        // 2. Gọi service layer
        CustomerDto savedCustomer = customerService.addCustomer(customerDto);
        
        // 3. Trả về kết quả
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        
    } catch (ResponseStatusException e) {
        // 4. Handle business logic errors
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
```

**Business Logic Processing:**
```java
// CustomerService.java - Add Customer Logic
public CustomerDto addCustomer(CustomerDto dto) {
    // 1. Validate phone number uniqueness
    validatePhone(dto.getSdt(), null);
    
    // 2. Convert DTO to Entity
    Customer entity = toEntity(dto);
    
    // 3. Generate next customer ID
    entity.setMaKh(generateNextCustomerId());
    
    // 4. Save to database
    Customer saved = customerRepository.save(entity);
    
    // 5. Convert back to DTO
    return toDto(saved);
}

private void validatePhone(String sdt, String excludeId) {
    Optional<Customer> existing = customerRepository.findBySdt(sdt);
    if (existing.isPresent() && 
        (excludeId == null || !existing.get().getMaKh().equals(excludeId))) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, 
                                        "Số điện thoại đã tồn tại!");
    }
}

private String generateNextCustomerId() {
    String lastId = jdbcTemplate.queryForObject(
        "SELECT TOP 1 maKh FROM Customer ORDER BY maKh DESC", 
        String.class);
    
    if (lastId == null || lastId.isEmpty()) {
        return "KH001";
    }
    
    int nextNumber = Integer.parseInt(lastId.substring(2)) + 1;
    return String.format("KH%03d", nextNumber);
}
```

#### **3.5 Cập Nhật Khách Hàng**

**✏️ Update Customer Flow:**

**Frontend Update Process:**
```java
// CustomerM.java - Update Logic
private void btnUpdateActionPerformed() {
    // 1. Check if customer is selected
    int selectedRow = tblCustomer.getSelectedRow();
    if (selectedRow == -1) {
        showError("Vui lòng chọn khách hàng cần sửa!");
        return;
    }
    
    // 2. Validate input
    if (!validateInput()) {
        return;
    }
    
    // 3. Get customer ID from selected row
    String maKh = (String) tblCustomer.getValueAt(selectedRow, 0);
    
    // 4. Create updated customer object
    Customer customer = new Customer();
    customer.setMaKh(maKh);
    customer.setTenKh(txtTenKH.getText().trim());
    customer.setSdt(txtSDT.getText().trim());
    
    try {
        // 5. Call update API
        Customer updatedCustomer = customerApiClient.update(maKh, customer);
        
        // 6. Refresh table
        loadCustomerData();
        
        // 7. Clear form
        clearForm();
        
        showSuccessMessage("Cập nhật khách hàng thành công!");
        
    } catch (IOException e) {
        handleApiError(e);
    }
}

// Table selection handler
private void tblCustomerMouseClicked() {
    int selectedRow = tblCustomer.getSelectedRow();
    if (selectedRow != -1) {
        // Fill form with selected customer data
        txtMaKH.setText((String) tblCustomer.getValueAt(selectedRow, 0));
        txtTenKH.setText((String) tblCustomer.getValueAt(selectedRow, 1));
        txtSDT.setText((String) tblCustomer.getValueAt(selectedRow, 2));
    }
}
```

**Backend Update Processing:**
```java
// CustomerController.java - PUT Endpoint
@PutMapping("/{maKh}")
public ResponseEntity<?> updateCustomer(@PathVariable String maKh, 
                                      @Valid @RequestBody CustomerDto customerDto, 
                                      BindingResult bindingResult) {
    // 1. Validation
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
        // 2. Update customer
        CustomerDto updated = customerService.updateCustomer(maKh, customerDto);
        return ResponseEntity.ok(updated);
        
    } catch (ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
```

**Service Update Logic:**
```java
// CustomerService.java - Update Business Logic
public CustomerDto updateCustomer(String maKh, CustomerDto dto) {
    // 1. Find existing customer
    Customer existing = customerRepository.findById(maKh)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng!"));
    
    // 2. Validate phone uniqueness (excluding current customer)
    validatePhone(dto.getSdt(), maKh);
    
    // 3. Update fields
    existing.setTenKh(dto.getTenKh());
    existing.setSdt(dto.getSdt());
    
    // 4. Save changes
    Customer saved = customerRepository.save(existing);
    
    // 5. Return updated DTO
    return toDto(saved);
}
```

#### **3.6 Xóa Khách Hàng**

**🗑️ Delete Customer Flow:**

**Frontend Delete Process:**
```java
// CustomerM.java - Delete Logic
private void btnDeleteActionPerformed() {
    // 1. Check selection
    int selectedRow = tblCustomer.getSelectedRow();
    if (selectedRow == -1) {
        showError("Vui lòng chọn khách hàng cần xóa!");
        return;
    }
    
    // 2. Confirmation dialog
    String customerName = (String) tblCustomer.getValueAt(selectedRow, 1);
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa khách hàng: " + customerName + "?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }
    
    // 3. Get customer ID
    String maKh = (String) tblCustomer.getValueAt(selectedRow, 0);
    
    try {
        // 4. Call delete API
        customerApiClient.delete(maKh);
        
        // 5. Refresh table
        loadCustomerData();
        
        // 6. Clear form
        clearForm();
        
        showSuccessMessage("Xóa khách hàng thành công!");
        
    } catch (IOException e) {
        handleApiError(e);
    }
}
```

**Backend Delete Processing:**
```java
// CustomerController.java - DELETE Endpoint
@DeleteMapping("/{maKh}")
public ResponseEntity<?> deleteCustomer(@PathVariable String maKh) {
    try {
        customerService.deleteCustomer(maKh);
        return ResponseEntity.ok().build();
        
    } catch (ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
```

#### **3.7 Tìm Kiếm Khách Hàng**

**🔍 Search Customer Flow:**

**Frontend Search:**
```java
// CustomerM.java - Search Logic
private void txtSearchKeyReleased() {
    String keyword = txtSearch.getText().trim();
    
    if (keyword.isEmpty()) {
        // Show all customers
        loadCustomerData();
        return;
    }
    
    try {
        // Search customers
        List<Customer> searchResults = customerApiClient.search(keyword);
        updateTableModel(searchResults);
        
    } catch (IOException e) {
        handleApiError(e);
    }
}
```

**Backend Search Processing:**
```java
// CustomerController.java - Search Endpoint
@GetMapping("/search")
public ResponseEntity<List<CustomerDto>> searchCustomers(@RequestParam String query) {
    List<CustomerDto> customers = customerService.searchCustomers(query);
    return ResponseEntity.ok(customers);
}

// CustomerService.java - Search Logic
public List<CustomerDto> searchCustomers(String query) {
    List<Customer> customers = customerRepository
        .findByTenKhContainingIgnoreCase(query);
    
    return customers.stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
}
```

### 👔 Luồng Quản Lý Nhân Viên

Luồng quản lý nhân viên tương tự như khách hàng với các điểm khác biệt:

#### **3.8 Đặc Điểm Riêng của Employee Management**

**📋 Additional Fields:**
- Lương (Salary) với validation
- Chức vụ (Position) với dropdown selection
- Tìm kiếm theo nhiều tiêu chí (tên, SĐT, khoảng lương)

**🔍 Advanced Search Features:**
```java
// EmployeeController.java - Multiple search endpoints
@GetMapping("/search/name")
public ResponseEntity<List<EmployeeDto>> searchByName(@RequestParam String name);

@GetMapping("/search/phone") 
public ResponseEntity<List<EmployeeDto>> searchByPhone(@RequestParam String phone);

@GetMapping("/search/salary")
public ResponseEntity<List<EmployeeDto>> searchBySalaryRange(
    @RequestParam BigDecimal min, @RequestParam BigDecimal max);
```

**💰 Salary Validation:**
```java
// EmployeeService.java - Salary validation
private void validateSalary(BigDecimal salary) {
    if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                                        "Lương phải lớn hơn 0!");
    }
    
    if (salary.compareTo(new BigDecimal("100000000")) > 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                                        "Lương không được vượt quá 100 triệu!");
    }
}
```

---

## 4. Chi Tiết Lý Thuyết về API

### 🌐 REST API Fundamentals

#### **4.1 REST Architecture Principles**

**📖 Representational State Transfer (REST)**
REST là một architectural style cho distributed systems, đặc biệt cho web services.

**🔧 Core Principles:**

1. **Client-Server Architecture**
   - Tách biệt concerns giữa client và server
   - Client xử lý UI, Server xử lý data storage
   - Improved portability và scalability

2. **Stateless**
   - Mỗi request từ client phải chứa đầy đủ thông tin
   - Server không lưu trữ client context
   - Improved reliability và scalability

3. **Cacheable**
   - Responses phải được đánh dấu cacheable hoặc non-cacheable
   - Giảm client-server interactions
   - Improved performance

4. **Uniform Interface**
   - Consistent interface giữa components
   - Simplified architecture
   - Improved visibility of interactions

5. **Layered System**
   - Client không biết nó connect trực tiếp với server
   - Có thể có intermediary layers (proxy, load balancer)
   - Improved scalability

#### **4.2 HTTP Methods và Semantic**

**📝 CRUD Operations Mapping:**

| HTTP Method | CRUD Operation | Idempotent | Safe | Request Body | Response Body |
|-------------|----------------|------------|------|--------------|---------------|
| GET         | Read          | Yes        | Yes  | No           | Yes           |
| POST        | Create        | No         | No   | Yes          | Yes           |
| PUT         | Update        | Yes        | No   | Yes          | Yes           |
| DELETE      | Delete        | Yes        | No   | No           | Optional      |

**🔍 Method Details:**

**GET - Retrieve Data**
```http
GET /api/customers
GET /api/customers/KH001
GET /api/customers/search?query=John
```
- **Idempotent**: Multiple identical requests có same effect
- **Safe**: Không thay đổi server state
- **Cacheable**: Response có thể cache

**POST - Create New Resource**
```http
POST /api/customers
Content-Type: application/json

{
  "tenKh": "Nguyen Van A",
  "sdt": "0123456789"
}
```
- **Non-idempotent**: Multiple requests tạo multiple resources
- **Not safe**: Thay đổi server state
- **Response**: 201 Created với resource mới

**PUT - Update Existing Resource**
```http
PUT /api/customers/KH001
Content-Type: application/json

{
  "tenKh": "Nguyen Van B", 
  "sdt": "0987654321"
}
```
- **Idempotent**: Multiple identical requests có same effect
- **Complete replacement**: Thay thế toàn bộ resource
- **Response**: 200 OK với updated resource

**DELETE - Remove Resource**
```http
DELETE /api/customers/KH001
```
- **Idempotent**: Multiple deletes có same effect
- **Response**: 204 No Content hoặc 200 OK

#### **4.3 HTTP Status Codes**

**✅ Success Codes (2xx)**
- **200 OK**: Request successful, response có body
- **201 Created**: Resource created successfully
- **204 No Content**: Request successful, no response body

**❌ Client Error Codes (4xx)**
- **400 Bad Request**: Invalid request syntax hoặc validation errors
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Access denied
- **404 Not Found**: Resource không tồn tại
- **409 Conflict**: Resource conflict (duplicate)

**🔥 Server Error Codes (5xx)**
- **500 Internal Server Error**: Unexpected server error
- **503 Service Unavailable**: Server temporarily unavailable

#### **4.4 Request/Response Format**

**📤 Request Format:**
```http
POST /api/customers HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: application/json

{
  "tenKh": "Nguyen Van A",
  "sdt": "0123456789"
}
```

**📥 Response Format:**
```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/customers/KH001

{
  "maKh": "KH001",
  "tenKh": "Nguyen Van A", 
  "sdt": "0123456789"
}
```

**❌ Error Response Format:**
```http
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "tenKh": "Tên khách hàng không được trống",
  "sdt": "Số điện thoại không hợp lệ"
}
```

#### **4.5 API Versioning**

**🔄 Versioning Strategies:**

1. **URL Path Versioning**
   ```
   /api/v1/customers
   /api/v2/customers
   ```

2. **Query Parameter Versioning**
   ```
   /api/customers?version=1
   ```

3. **Header Versioning**
   ```
   Accept: application/vnd.api+json;version=1
   ```

4. **Content Type Versioning**
   ```
   Content-Type: application/vnd.customer.v1+json
   ```

#### **4.6 API Security**

**🔐 Authentication Methods:**

1. **Basic Authentication**
   ```http
   Authorization: Basic dXNlcjpwYXNzd29yZA==
   ```

2. **Bearer Token (JWT)**
   ```http
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

3. **API Key**
   ```http
   X-API-Key: your-api-key-here
   ```

**🛡️ Security Best Practices:**
- **HTTPS**: Encrypt data in transit
- **Input Validation**: Validate all input data
- **Rate Limiting**: Prevent abuse
- **CORS**: Control cross-origin requests
- **Authentication**: Verify user identity
- **Authorization**: Control access to resources

#### **4.7 API Documentation**

**📚 Documentation Components:**

1. **Endpoint Description**
   - Purpose và functionality
   - Business logic explanation

2. **Request Specification**
   - HTTP method và URL
   - Headers required
   - Request body schema
   - Query parameters

3. **Response Specification**
   - Success response format
   - Error response format
   - Status codes

4. **Examples**
   - Sample requests
   - Sample responses
   - Different scenarios

**📋 QLNS API Example Documentation:**

```yaml
# Customer Management API

## Get All Customers
GET /api/customers

### Description
Retrieves a list of all customers in the system.

### Response
- **200 OK**: Array of customer objects
- **500 Internal Server Error**: Server error

### Example Response
```json
[
  {
    "maKh": "KH001",
    "tenKh": "Nguyen Van A",
    "sdt": "0123456789"
  }
]
```

## Create Customer
POST /api/customers

### Description
Creates a new customer in the system.

### Request Body
```json
{
  "tenKh": "string (required, max 100 chars)",
  "sdt": "string (required, 10-11 digits)"
}
```

### Response
- **201 Created**: Customer created successfully
- **400 Bad Request**: Validation errors
- **409 Conflict**: Phone number already exists

### Example Request
```json
{
  "tenKh": "Nguyen Van A",
  "sdt": "0123456789"
}
```

### Example Response
```json
{
  "maKh": "KH001",
  "tenKh": "Nguyen Van A",
  "sdt": "0123456789"
}
```
```

---

## 5. Công Nghệ Sử Dụng & Lý Thuyết

### ⚡ Backend Technologies

#### **5.1 Spring Boot Framework**

**🌱 Spring Boot Overview:**
Spring Boot là framework để tạo production-ready Spring applications với minimal configuration.

**🎯 Core Features:**

1. **Auto-Configuration**
   ```java
   @SpringBootApplication // Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
   public class QlinsBeApplication {
       public static void main(String[] args) {
           SpringApplication.run(QlinsBeApplication.class, args);
       }
   }
   ```
   - Tự động config dựa trên classpath dependencies
   - Giảm boilerplate configuration
   - Convention over configuration

2. **Embedded Server**
   ```yaml
   server:
     port: 8080
     servlet:
       context-path: /api
   ```
   - Tomcat embedded mặc định
   - Không cần deploy WAR file
   - Self-contained executable JAR

3. **Starter Dependencies**
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   ```
   - Pre-configured dependency sets
   - Simplified dependency management
   - Version compatibility

**🔧 Spring Core Concepts:**

**Inversion of Control (IoC)**
```java
@Service
public class CustomerService {
    @Autowired // Constructor injection preferred
    private CustomerRepository customerRepository;
    
    // Spring manages object lifecycle và dependency injection
}
```

**Aspect-Oriented Programming (AOP)**
```java
@RestController
public class CustomerController {
    @GetMapping // AOP-based request mapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        // Cross-cutting concerns (logging, security, transactions) 
        // handled by AOP
    }
}
```

**Component Scanning**
```java
@SpringBootApplication
// Automatically scans for @Component, @Service, @Repository, @Controller
public class QlinsBeApplication {
    // Spring creates và manages beans
}
```

#### **5.2 Spring MVC Architecture**

**🏗️ MVC Pattern Implementation:**

```
Request → DispatcherServlet → HandlerMapping → Controller → Service → Repository → Database
                            ← ViewResolver     ← Model     ← Service ← Repository ← Database
```

**🎮 Controller Layer:**
```java
@RestController // @Controller + @ResponseBody
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping // Maps HTTP GET requests
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        // Handler method
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
```

**⚙️ Service Layer:**
```java
@Service // Business logic component
@Transactional // Transaction management
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<CustomerDto> getAllCustomers() {
        // Business logic processing
        return customerRepository.findAll()
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
    }
}
```

**💾 Repository Layer:**
```java
@Repository // Data access component
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // Spring Data JPA provides implementation
    List<Customer> findByTenKhContainingIgnoreCase(String tenKh);
    Optional<Customer> findBySdt(String sdt);
}
```

#### **5.3 Spring Data JPA**

**🗃️ JPA (Java Persistence API):**
JPA là specification để manage relational data trong Java applications.

**🔄 Hibernate (JPA Implementation):**
```java
@Entity // JPA entity
@Table(name = "Customer")
public class Customer {
    @Id // Primary key
    @Column(name = "maKh")
    private String maKh;
    
    @Column(name = "tenKh", nullable = false, length = 100)
    @NotBlank(message = "Tên khách hàng không được trống")
    private String tenKh;
    
    @Column(name = "sdt", unique = true, length = 11)
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    private String sdt;
}
```

**🔍 Repository Pattern:**
```java
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // Derived query methods
    List<Customer> findByTenKhContainingIgnoreCase(String tenKh);
    Optional<Customer> findBySdt(String sdt);
    
    // Custom query
    @Query("SELECT c FROM Customer c WHERE c.tenKh LIKE %:keyword% OR c.sdt LIKE %:keyword%")
    List<Customer> searchByKeyword(@Param("keyword") String keyword);
    
    // Native query
    @Query(value = "SELECT * FROM Customer WHERE sdt = ?1", nativeQuery = true)
    Optional<Customer> findByPhoneNative(String sdt);
}
```

**💼 Transaction Management:**
```java
@Service
@Transactional // Class-level transaction
public class CustomerService {
    
    @Transactional(readOnly = true) // Read-only transaction
    public List<CustomerDto> getAllCustomers() {
        // Read operation
    }
    
    @Transactional // Read-write transaction
    public CustomerDto addCustomer(CustomerDto dto) {
        // Write operation với automatic rollback on exception
    }
}
```

#### **5.4 Bean Validation (JSR-303)**

**✅ Validation Annotations:**
```java
public class CustomerDto {
    @NotBlank(message = "Tên khách hàng không được trống")
    @Size(max = 100, message = "Tên khách hàng không được vượt quá 100 ký tự")
    private String tenKh;
    
    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String sdt;
}
```

**🔍 Controller Validation:**
```java
@PostMapping
public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customerDto, 
                                   BindingResult bindingResult) {
    // @Valid triggers validation
    if (bindingResult.hasErrors()) {
        // Handle validation errors
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    // Process valid data
    CustomerDto savedCustomer = customerService.addCustomer(customerDto);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
}
```

#### **5.5 Jackson JSON Processing**

**📄 JSON Serialization/Deserialization:**
```java
// Automatic JSON conversion
@RestController
public class CustomerController {
    
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        // Jackson automatically converts JSON → CustomerDto (deserialization)
        CustomerDto saved = customerService.addCustomer(customerDto);
        // Jackson automatically converts CustomerDto → JSON (serialization)
        return ResponseEntity.ok(saved);
    }
}
```

**🎛️ Jackson Annotations:**
```java
public class CustomerDto {
    @JsonProperty("ma_kh") // Custom JSON field name
    private String maKh;
    
    @JsonIgnore // Exclude from JSON
    private String internalField;
    
    @JsonFormat(pattern = "dd/MM/yyyy") // Date format
    private LocalDate ngayTao;
}
```

#### **5.6 SQL Server Integration**

**🔌 Database Configuration:**
```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=QLNS_API;encrypt=false
    driverClassName: com.microsoft.sqlserver.jdbc.SQLServerDriver
    username: user1
    password: password123
    
  jpa:
    hibernate:
      ddl-auto: validate # validate, update, create, create-drop
    show-sql: true # Show SQL queries in log
    properties:
      hibernate:
        dialect: org.hibernate.dialect.SQLServerDialect
        format_sql: true
```

**📊 Connection Pooling:**
```yaml
spring:
  datasource:
    hikari: # HikariCP connection pool
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

### 🖥️ Frontend Technologies

#### **5.7 Java Swing Framework**

**🎨 Swing Architecture:**

**Component Hierarchy:**
```
JFrame (Top-level container)
├── JPanel (Intermediate container)
│   ├── JLabel (Component)
│   ├── JTextField (Component)
│   └── JButton (Component)
└── JMenuBar (Component)
```

**Event-Driven Programming:**
```java
public class CustomerM extends JFrame {
    private JButton btnAdd;
    
    private void initComponents() {
        btnAdd = new JButton("Thêm");
        
        // Event listener registration
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddActionPerformed(e);
            }
        });
        
        // Lambda expression (Java 8+)
        btnAdd.addActionListener(e -> btnAddActionPerformed(e));
    }
    
    private void btnAddActionPerformed(ActionEvent e) {
        // Event handling logic
    }
}
```

**Layout Managers:**
```java
// BorderLayout
setLayout(new BorderLayout());
add(toolbar, BorderLayout.NORTH);
add(content, BorderLayout.CENTER);
add(statusBar, BorderLayout.SOUTH);

// GridBagLayout (more complex)
setLayout(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.gridx = 0; gbc.gridy = 0;
add(label, gbc);
```

#### **5.8 MVP Pattern Implementation**

**🎯 MVP (Model-View-Presenter):**

**View (Passive View):**
```java
public class CustomerM extends JFrame {
    private MainMenuManagerPresenter presenter;
    
    public CustomerM(MainMenuManagerPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }
    
    // View methods
    public void showCustomers(List<Customer> customers) {
        // Update table model
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
```

**Presenter (Controller Logic):**
```java
public class CustomerPresenter {
    private CustomerM view;
    private CustomerApiClient apiClient;
    
    public CustomerPresenter(CustomerM view) {
        this.view = view;
        this.apiClient = new CustomerApiClient();
    }
    
    public void loadCustomers() {
        try {
            List<Customer> customers = apiClient.getAll();
            view.showCustomers(customers);
        } catch (IOException e) {
            view.showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
}
```

**Model (Data Objects):**
```java
public class Customer {
    private String maKh;
    private String tenKh;
    private String sdt;
    
    // Constructor, getters, setters
    // Business logic methods if needed
}
```

#### **5.9 HTTP Client (OkHttp)**

**🌐 HTTP Communication:**

**Basic Request:**
```java
public class CustomerApiClient {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    public List<Customer> getAll() throws IOException {
        Request request = new Request.Builder()
            .url("http://localhost:8080/api/customers")
            .build();
            
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            
            String json = response.body().string();
            return mapper.readValue(json, new TypeReference<List<Customer>>(){});
        }
    }
}
```

**POST Request with JSON:**
```java
public Customer add(Customer customer) throws IOException {
    String json = mapper.writeValueAsString(customer);
    RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
    
    Request request = new Request.Builder()
        .url("http://localhost:8080/api/customers")
        .post(body)
        .build();
        
    try (Response response = client.newCall(request).execute()) {
        if (!response.isSuccessful()) {
            throw new IOException("Request failed: " + response.body().string());
        }
        
        return mapper.readValue(response.body().string(), Customer.class);
    }
}
```

**Request with Query Parameters:**
```java
public List<Customer> search(String keyword) throws IOException {
    HttpUrl url = HttpUrl.parse("http://localhost:8080/api/customers/search")
                         .newBuilder()
                         .addQueryParameter("query", keyword)
                         .build();
                         
    Request request = new Request.Builder()
        .url(url)
        .build();
        
    try (Response response = client.newCall(request).execute()) {
        return mapper.readValue(response.body().string(), 
                               new TypeReference<List<Customer>>(){});
    }
}
```

#### **5.10 JSON Processing (Jackson)**

**🔄 Object Mapping:**
```java
public class ApiClientBase {
    protected final ObjectMapper mapper = new ObjectMapper();
    
    // Configure ObjectMapper
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    
    // Serialize object to JSON
    protected String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }
    
    // Deserialize JSON to object
    protected <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }
    
    // Deserialize JSON to List
    protected <T> List<T> fromJsonList(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, 
            mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
```

### 🛠️ Development Tools

#### **5.11 Maven Build Tool**

**📦 Project Structure:**
```
pom.xml (Project Object Model)
├── Dependencies
├── Plugins  
├── Properties
└── Build Configuration
```

**Dependencies Management:**
```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- SQL Server JDBC Driver -->
    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

**Build Lifecycle:**
```bash
mvn clean      # Clean target directory
mvn compile    # Compile source code
mvn test       # Run unit tests
mvn package    # Create JAR/WAR file
mvn install    # Install to local repository
mvn deploy     # Deploy to remote repository
```

#### **5.12 Lombok Library**

**🎭 Code Generation:**
```java
@Data // Generates getters, setters, toString, equals, hashCode
@Builder // Generates builder pattern
@NoArgsConstructor // Generates no-args constructor
@AllArgsConstructor // Generates all-args constructor
public class CustomerDto {
    private String maKh;
    private String tenKh;
    private String sdt;
}

// Usage with builder
CustomerDto customer = CustomerDto.builder()
                                 .maKh("KH001")
                                 .tenKh("Nguyen Van A")
                                 .sdt("0123456789")
                                 .build();
```

**🔧 Annotation Processing:**
Lombok sử dụng annotation processing để generate code tại compile time:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### 🎯 Best Practices & Design Principles

#### **5.13 SOLID Principles Implementation**

**S - Single Responsibility Principle:**
```java
// Bad - Multiple responsibilities
public class CustomerController {
    public void addCustomer() {
        // HTTP handling
        // Business logic
        // Database operations
        // Email sending
    }
}

// Good - Single responsibility
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto dto) {
        // Only handles HTTP requests/responses
        CustomerDto saved = customerService.addCustomer(dto);
        return ResponseEntity.ok(saved);
    }
}
```

**O - Open/Closed Principle:**
```java
// Open for extension, closed for modification
public interface PaymentProcessor {
    void processPayment(BigDecimal amount);
}

public class CreditCardProcessor implements PaymentProcessor {
    public void processPayment(BigDecimal amount) {
        // Credit card logic
    }
}

public class PayPalProcessor implements PaymentProcessor {
    public void processPayment(BigDecimal amount) {
        // PayPal logic
    }
}
```

**L - Liskov Substitution Principle:**
```java
// Subclasses should be substitutable for their base classes
List<String> list = new ArrayList<>(); // Can be substituted with LinkedList
list = new LinkedList<>(); // Without breaking functionality
```

**I - Interface Segregation Principle:**
```java
// Bad - Fat interface
public interface CustomerOperations {
    void add();
    void update();
    void delete();
    void generateReport(); // Not all clients need this
    void exportToExcel();  // Not all clients need this
}

// Good - Segregated interfaces
public interface CustomerCRUD {
    void add();
    void update();  
    void delete();
}

public interface CustomerReporting {
    void generateReport();
    void exportToExcel();
}
```

**D - Dependency Inversion Principle:**
```java
// High-level modules should not depend on low-level modules
@Service
public class CustomerService {
    private final CustomerRepository repository; // Depends on abstraction
    
    public CustomerService(CustomerRepository repository) {
        this.repository = repository; // Dependency injection
    }
}
```

#### **5.14 Exception Handling Strategies**

**🚨 Global Exception Handler:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```

**🔧 Service Layer Exception Handling:**
```java
@Service
public class CustomerService {
    
    public CustomerDto getCustomerById(String maKh) {
        Customer customer = customerRepository.findById(maKh)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng với ID: " + maKh));
        
        return toDto(customer);
    }
}
```

---

## 📊 Kết Luận

Hệ thống QLNS là một ứng dụng full-stack hoàn chỉnh sử dụng các công nghệ hiện đại:

### 🎯 Điểm Mạnh của Kiến Trúc

1. **Separation of Concerns**: Clear separation giữa frontend và backend
2. **Scalability**: RESTful API cho phép multiple clients
3. **Maintainability**: Layered architecture dễ maintain và extend
4. **Testability**: Loosely coupled components dễ test
5. **Security**: Input validation và error handling
6. **Performance**: Connection pooling và caching strategies

### 🚀 Khả Năng Mở Rộng

1. **Mobile App**: Có thể tạo mobile app sử dụng cùng API
2. **Web Frontend**: Có thể thay thế Swing bằng React/Angular
3. **Microservices**: Có thể tách thành multiple services
4. **Cloud Deployment**: Có thể deploy lên AWS/Azure
5. **Real-time Features**: Có thể thêm WebSocket cho real-time updates

### 📚 Kiến Thức Cần Thiết

1. **Java Fundamentals**: OOP, Collections, Streams
2. **Spring Framework**: IoC, AOP, MVC
3. **Database**: SQL, JPA/Hibernate
4. **Web Technologies**: HTTP, REST, JSON
5. **Software Engineering**: Design patterns, SOLID principles
6. **Tools**: Maven, Git, IDE

Hệ thống này là một excellent example của modern Java application development với best practices và proven technologies.
