package com.soutenance.service;

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
import java.util.List;

public interface AdminService {
    PaginatedResponse<UserResponse> getUsers(int page, int limit, String role);
    UserResponse createUser(UserRequest request);
    List<UserResponse> bulkCreateUsers(BulkUsersRequest request);
    UserResponse updateUser(String id, UserRequest request);
    void deleteUser(String id);

    List<DepartmentResponse> getDepartments();
    DepartmentResponse createDepartment(DepartmentRequest request);
    DepartmentResponse updateDepartment(String id, DepartmentRequest request);
    void deleteDepartment(String id);

    List<SessionResponse> getSessions();
    SessionResponse createSession(SessionRequest request);
    SessionResponse updateSession(String id, SessionRequest request);
    void deleteSession(String id);

    List<RoomResponse> getRooms();
    RoomResponse createRoom(RoomRequest request);
    List<RoomResponse> bulkCreateRooms(BulkRoomsRequest request);
    RoomResponse updateRoom(String id, RoomRequest request);
    void deleteRoom(String id);

    DashboardStatsDto getStats();
    List<AuditLogResponse> getAuditLogs();
}
