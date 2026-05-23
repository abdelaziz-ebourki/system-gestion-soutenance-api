package com.system_gestion_soutenance.api.user.controller;

import com.system_gestion_soutenance.api.common.dto.PaginatedResponse;
import com.system_gestion_soutenance.api.user.dto.BulkCreateRequest;
import com.system_gestion_soutenance.api.user.dto.CreateUserRequest;
import com.system_gestion_soutenance.api.user.dto.UpdateUserRequest;
import com.system_gestion_soutenance.api.user.dto.UserDto;
import com.system_gestion_soutenance.api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public PaginatedResponse<UserDto> listUsers(
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return userService.listUsers(role, page, limit);
    }

    @GetMapping("/users/teachers-list")
    public List<UserDto> listAllTeachers() {
        return userService.listAllByRole("teacher");
    }

    @GetMapping("/users/students-list")
    public List<UserDto> listAllStudents() {
        return userService.listAllByRole("student");
    }

    @GetMapping("/students")
    public PaginatedResponse<UserDto> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return userService.listUsers("student", page, limit);
    }

    @GetMapping("/teachers")
    public PaginatedResponse<UserDto> listTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return userService.listUsers("teacher", page, limit);
    }

    @GetMapping("/coordinators")
    public PaginatedResponse<UserDto> listCoordinators(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return userService.listUsers("coordinator", page, limit);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/students")
    public ResponseEntity<UserDto> createStudent(@Valid @RequestBody CreateUserRequest request) {
        if (request.role() == null || request.role().isBlank()) {
            request = new CreateUserRequest(request.lastName(), request.firstName(),
                    request.email(), "student", request.cne(), request.majorId(),
                    request.levelId(), null, null);
        }
        UserDto user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/teachers")
    public ResponseEntity<UserDto> createTeacher(@Valid @RequestBody CreateUserRequest request) {
        if (request.role() == null || request.role().isBlank()) {
            request = new CreateUserRequest(request.lastName(), request.firstName(),
                    request.email(), "teacher", null, null,
                    null, request.gradeId(), request.departmentId());
        }
        UserDto user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/coordinators")
    public ResponseEntity<UserDto> createCoordinator(@Valid @RequestBody CreateUserRequest request) {
        if (request.role() == null || request.role().isBlank()) {
            request = new CreateUserRequest(request.lastName(), request.firstName(),
                    request.email(), "coordinator", null, null,
                    null, null, null);
        }
        UserDto user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/bulk")
    public ResponseEntity<List<UserDto>> bulkCreate(@Valid @RequestBody BulkCreateRequest request) {
        List<UserDto> users = userService.bulkCreate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
