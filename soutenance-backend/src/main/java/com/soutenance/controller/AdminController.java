package com.soutenance.controller;

import com.soutenance.dto.Dtos.AuditLogResponse;
import com.soutenance.dto.Dtos.BulkRoomsRequest;
import com.soutenance.dto.Dtos.BulkUsersRequest;
import com.soutenance.dto.Dtos.DashboardStatsDto;
import com.soutenance.dto.Dtos.DepartmentRequest;
import com.soutenance.dto.Dtos.DepartmentResponse;
import com.soutenance.dto.Dtos.PaginatedResponse;
import com.soutenance.dto.Dtos.RoomRequest;
import com.soutenance.dto.Dtos.RoomResponse;
import com.soutenance.dto.Dtos.SessionRequest;
import com.soutenance.dto.Dtos.SessionResponse;
import com.soutenance.dto.Dtos.UserRequest;
import com.soutenance.dto.Dtos.UserResponse;
import com.soutenance.service.AdminService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public PaginatedResponse<UserResponse> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(required = false) String role
    ) {
        return adminService.getUsers(page, limit, role);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return adminService.createUser(request);
    }

    @PostMapping("/users/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserResponse> bulkCreateUsers(@Valid @RequestBody BulkUsersRequest request) {
        return adminService.bulkCreateUsers(request);
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UserRequest request) {
        return adminService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
    }

    @GetMapping("/departments")
    public List<DepartmentResponse> getDepartments() {
        return adminService.getDepartments();
    }

    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(@Valid @RequestBody DepartmentRequest request) {
        return adminService.createDepartment(request);
    }

    @PutMapping("/departments/{id}")
    public DepartmentResponse updateDepartment(@PathVariable String id, @Valid @RequestBody DepartmentRequest request) {
        return adminService.updateDepartment(id, request);
    }

    @DeleteMapping("/departments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable String id) {
        adminService.deleteDepartment(id);
    }

    @GetMapping("/sessions")
    public List<SessionResponse> getSessions() {
        return adminService.getSessions();
    }

    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@Valid @RequestBody SessionRequest request) {
        return adminService.createSession(request);
    }

    @PutMapping("/sessions/{id}")
    public SessionResponse updateSession(@PathVariable String id, @Valid @RequestBody SessionRequest request) {
        return adminService.updateSession(id, request);
    }

    @DeleteMapping("/sessions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable String id) {
        adminService.deleteSession(id);
    }

    @GetMapping("/rooms")
    public List<RoomResponse> getRooms() {
        return adminService.getRooms();
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@Valid @RequestBody RoomRequest request) {
        return adminService.createRoom(request);
    }

    @PostMapping("/rooms/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<RoomResponse> bulkCreateRooms(@Valid @RequestBody BulkRoomsRequest request) {
        return adminService.bulkCreateRooms(request);
    }

    @PutMapping("/rooms/{id}")
    public RoomResponse updateRoom(@PathVariable String id, @Valid @RequestBody RoomRequest request) {
        return adminService.updateRoom(id, request);
    }

    @DeleteMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable String id) {
        adminService.deleteRoom(id);
    }

    @GetMapping("/stats")
    public DashboardStatsDto getStats() {
        return adminService.getStats();
    }

    @GetMapping("/audit-logs")
    public List<AuditLogResponse> getAuditLogs() {
        return adminService.getAuditLogs();
    }
}
