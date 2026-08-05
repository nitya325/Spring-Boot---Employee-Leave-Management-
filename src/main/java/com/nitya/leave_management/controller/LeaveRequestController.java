package com.nitya.leave_management.controller;


import com.nitya.leave_management.dto.LeaveRequestDTO;
import com.nitya.leave_management.dto.LeaveResponseDTO;
import com.nitya.leave_management.entity.LeaveStatus;
import com.nitya.leave_management.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping
    public ResponseEntity<LeaveResponseDTO> applyLeave(@Valid @RequestBody LeaveRequestDTO dto) {
        LeaveResponseDTO response = leaveRequestService.applyLeave(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveResponseDTO>> getLeavesByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeavesByEmployee(employeeId));
    }
    @PutMapping("/{id}/status")
public ResponseEntity<LeaveResponseDTO> updateLeaveStatus(
        @PathVariable Long id, @RequestParam LeaveStatus status) {
    return ResponseEntity.ok(leaveRequestService.updateLeaveStatus(id, status));
}
}