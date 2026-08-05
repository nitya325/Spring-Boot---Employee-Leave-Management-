package com.nitya.leave_management.service;
import com.nitya.leave_management.exception.ResourceNotFoundException;
import com.nitya.leave_management.dto.EmployeeRequest;
import com.nitya.leave_management.entity.Employee;
import com.nitya.leave_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setLeaveBalance(request.getLeaveBalance());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
}

    

public Employee updateEmployee(Long id, EmployeeRequest request) {
    Employee employee = getEmployeeById(id);
    employee.setName(request.getName());
    employee.setEmail(request.getEmail());
    employee.setDepartment(request.getDepartment());
    employee.setLeaveBalance(request.getLeaveBalance());
    return employeeRepository.save(employee);
}

public void deleteEmployee(Long id) {
    Employee employee = getEmployeeById(id);
    employeeRepository.delete(employee);
}

public Page<Employee> getAllEmployees(Pageable pageable) {
    return employeeRepository.findAll(pageable);
}
public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

}

