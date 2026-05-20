package com.soutenance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class Dtos {

    private Dtos() {
    }

    public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
    ) {
    }

    public record MessageResponse(String message) {
    }

    public record PaginatedResponse<T>(List<T> items, long total, int pageCount) {
    }

    public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
    ) {
    }

    public record AuthResponse(UserResponse user, String token, long expiresAt) {
    }

    public record VerifyAccountRequest(
        @NotBlank String token,
        @NotBlank @Size(min = 8) String password
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record UserRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        String password,
        @NotBlank String role,
        Boolean isActive,
        String cne,
        String filiereId,
        String levelId,
        String projectId,
        String gradeId,
        String departmentId
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String role,
        Boolean isActive,
        String cne,
        String filiereId,
        String levelId,
        String projectId,
        String gradeId,
        String departmentId
    ) {
    }

    public record BulkUsersRequest(
        @NotEmpty List<Map<String, Object>> users,
        @NotBlank String role
    ) {
    }

    public record DepartmentRequest(
        @NotBlank String name,
        @NotBlank String code,
        @NotBlank String headId
    ) {
    }

    public record DepartmentResponse(String id, String name, String code, String headId) {
    }

    public record SessionRequest(
        @NotBlank String name,
        @NotBlank String type,
        @NotBlank String status,
        @NotBlank String startDate,
        @NotBlank String endDate
    ) {
    }

    public record SessionResponse(
        String id,
        String name,
        String type,
        String status,
        String startDate,
        String endDate
    ) {
    }

    public record RoomRequest(
        @NotBlank String name,
        @NotNull @Min(1) Integer capacity,
        @NotBlank String building
    ) {
    }

    public record RoomResponse(String id, String name, Integer capacity, String building) {
    }

    public record BulkRoomsRequest(@NotEmpty List<Map<String, Object>> rooms) {
    }

    public record SimpleNameRequest(@NotBlank String name) {
    }

    public record SimpleNameResponse(String id, String name) {
    }

    public record DefenseSettingsDto(
        @NotBlank String startTime,
        @NotBlank String endTime,
        @NotNull @Min(1) @Max(180) Integer defenseDuration,
        @NotNull @Min(0) Integer breakDuration,
        @NotBlank String groupCreationStartDate,
        @NotBlank String groupCreationEndDate
    ) {
    }

    public record DashboardStatsDto(
        long totalStudents,
        long totalTeachers,
        long totalDepartments,
        long totalRooms,
        long activeSessions,
        long upcomingDefenses
    ) {
    }

    public record AuditLogResponse(
        String id,
        String action,
        String entity,
        String entityId,
        String adminEmail,
        String details,
        String timestamp
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ProjectRequest(
        @NotBlank String title,
        String description,
        @NotBlank String supervisorId,
        @NotEmpty List<String> studentIds,
        List<String> studentNames,
        String supervisorName,
        String status
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ProjectResponse(
        String id,
        String title,
        String description,
        List<String> studentIds,
        List<String> studentNames,
        String supervisorId,
        String supervisorName,
        String status
    ) {
    }

    public record CoordinatorStatsDto(
        long totalProjects,
        long totalGroups,
        long totalJuries,
        long scheduledDefenses
    ) {
    }

    public record GroupRequest(
        @NotBlank String projectId,
        @NotEmpty List<String> studentIds,
        @NotBlank String sessionId
    ) {
    }

    public record GroupResponse(String id, String projectId, List<String> studentIds, String sessionId) {
    }

    public record JuryRequest(
        @NotBlank String projectId,
        String projectTitle,
        @NotBlank String presidentId,
        String presidentName,
        @NotBlank String reporterId,
        String reporterName,
        @NotBlank String examinerId,
        String examinerName
    ) {
    }

    public record JuryResponse(
        String id,
        String projectId,
        String projectTitle,
        String presidentId,
        String presidentName,
        String reporterId,
        String reporterName,
        String examinerId,
        String examinerName
    ) {
    }

    public record ScheduleCard(String id, String title) {
    }

    public record ScheduleRequest(@NotNull Map<String, @Valid ScheduleCard> schedule) {
    }

    public record TeacherStatsDto(
        long upcomingDefenses,
        long pendingEvaluations,
        long declaredUnavailabilitySlots,
        long juryAssignments
    ) {
    }

    public record TeacherDefenseResponse(
        String id,
        String projectId,
        String projectTitle,
        List<String> studentNames,
        String date,
        String startTime,
        String endTime,
        String roomName,
        String role,
        String status
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TeacherEvaluationResponse(
        String id,
        String defenseId,
        String projectTitle,
        List<String> studentNames,
        String role,
        BigDecimal score,
        String comment,
        String status,
        String submittedAt
    ) {
    }

    public record TeacherEvaluationSubmitRequest(
        @NotNull @Min(0) @Max(20) BigDecimal score,
        String comment
    ) {
    }

    public record TeacherUnavailabilityDto(Map<String, List<String>> slotsByDate) {
    }

    public record StudentStatsDto(
        long documentCount,
        long missingDocuments,
        long groupMembers,
        String defenseStatus
    ) {
    }

    public record JuryMemberDto(String name, String role) {
    }

    public record StudentDefenseResultDto(String decision, BigDecimal score) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record StudentDefenseDetailsDto(
        String projectTitle,
        String projectDescription,
        String supervisorName,
        List<JuryMemberDto> juryMembers,
        String date,
        String startTime,
        String endTime,
        String roomName,
        String status,
        String convocationUrl,
        StudentDefenseResultDto result
    ) {
    }

    public record StudentGroupMemberDto(String id, String fullName, String email, String role) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record StudentGroupDetailsDto(
        String id,
        String groupName,
        String projectTitle,
        String supervisorName,
        List<StudentGroupMemberDto> members
    ) {
    }

    public record AvailableGroupDto(String id, String groupName, int memberCount) {
    }

    public record StudentGroupWorkspaceDto(
        StudentGroupDetailsDto currentGroup,
        List<AvailableGroupDto> availableGroups,
        String groupCreationStartDate,
        String groupCreationEndDate,
        boolean isGroupCreationOpen
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record StudentDocumentResponse(
        String id,
        String name,
        String type,
        String deadline,
        String status,
        String submittedAt
    ) {
    }
}
