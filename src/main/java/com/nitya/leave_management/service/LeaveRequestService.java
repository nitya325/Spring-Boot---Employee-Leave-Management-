package com.nitya.leave_management.service;

import com.nitya.leave_management.dto.LeaveRequestDTO;
import com.nitya.leave_management.dto.LeaveResponseDTO;
import com.nitya.leave_management.entity.Employee;
import com.nitya.leave_management.entity.LeaveRequest;
import com.nitya.leave_management.entity.LeaveStatus;
import com.nitya.leave_management.exception.ResourceNotFoundException;
import com.nitya.leave_management.repository.EmployeeRepository;
import com.nitya.leave_management.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                                EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public LeaveResponseDTO applyLeave(LeaveRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + dto.getEmployeeId()));

        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        if (days <= 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (employee.getLeaveBalance() < days) {
            throw new IllegalArgumentException("Insufficient leave balance");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return toDTO(saved);
    }

    public List<LeaveResponseDTO> getLeavesByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private LeaveResponseDTO toDTO(LeaveRequest lr) {
        return new LeaveResponseDTO(
                lr.getId(),
                lr.getEmployee().getId(),
                lr.getEmployee().getName(),
                lr.getStartDate(),
                lr.getEndDate(),
                lr.getReason(),
                lr.getStatus()
        );
    }
    @Transactional
public LeaveResponseDTO updateLeaveStatus(Long leaveId, LeaveStatus newStatus) {
    LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveId));

    if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
        throw new IllegalStateException("Leave request already processed");
    }

    if (newStatus == LeaveStatus.APPROVED) {
        Employee employee = leaveRequest.getEmployee();
        long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

        if (employee.getLeaveBalance() < days) {
            throw new IllegalArgumentException("Insufficient leave balance");
        }
        employee.setLeaveBalance(employee.getLeaveBalance() - (int) days);
        employeeRepository.save(employee);
    }

    leaveRequest.setStatus(newStatus);
    LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
    return toDTO(updated);
}
}