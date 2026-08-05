package com.nitya.leave_management.controller;

import com.nitya.leave_management.dto.EmployeeRequest;
import com.nitya.leave_management.entity.Employee;
import com.nitya.leave_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@CrossOrigin(origins = {"http://localhost:5173", "https://YOUR-RENDER-FRONTEND-URL.onrender.com"})

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

@GetMapping
public ResponseEntity<Page<Employee>> getAllEmployees(Pageable pageable) {
    return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
}

@GetMapping("/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id));
}

@PutMapping("/{id}")
public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(employeeService.updateEmployee(id, request));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.noContent().build();
}

}