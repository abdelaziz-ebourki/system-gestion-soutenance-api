package com.soutenance.mapper;

import com.soutenance.dto.Dtos.AuditLogResponse;
import com.soutenance.dto.Dtos.DefenseSettingsDto;
import com.soutenance.dto.Dtos.DepartmentResponse;
import com.soutenance.dto.Dtos.RoomResponse;
import com.soutenance.dto.Dtos.SessionResponse;
import com.soutenance.dto.Dtos.SimpleNameResponse;
import com.soutenance.entity.AuditLog;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Department;
import com.soutenance.entity.Filiere;
import com.soutenance.entity.Grade;
import com.soutenance.entity.Level;
import com.soutenance.entity.Room;
import com.soutenance.entity.Session;
import com.soutenance.util.DateTimeUtil;
import org.springframework.stereotype.Component;

@Component
public class AcademicMapper {

    public DepartmentResponse toDepartmentResponse(Department department) {
        return new DepartmentResponse(
            department.getId(),
            department.getName(),
            department.getCode(),
            department.getHead() == null ? null : department.getHead().getId()
        );
    }

    public SessionResponse toSessionResponse(Session session) {
        return new SessionResponse(
            session.getId(),
            session.getName(),
            session.getType(),
            session.getStatus(),
            DateTimeUtil.date(session.getStartDate()),
            DateTimeUtil.date(session.getEndDate())
        );
    }

    public RoomResponse toRoomResponse(Room room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity(), room.getBuilding());
    }

    public SimpleNameResponse toSimpleResponse(Filiere filiere) {
        return new SimpleNameResponse(filiere.getId(), filiere.getName());
    }

    public SimpleNameResponse toSimpleResponse(Level level) {
        return new SimpleNameResponse(level.getId(), level.getName());
    }

    public SimpleNameResponse toSimpleResponse(Grade grade) {
        return new SimpleNameResponse(grade.getId(), grade.getName());
    }

    public DefenseSettingsDto toSettingsResponse(DefenseSettings settings) {
        return new DefenseSettingsDto(
            DateTimeUtil.time(settings.getStartTime()),
            DateTimeUtil.time(settings.getEndTime()),
            settings.getDefenseDuration(),
            settings.getBreakDuration(),
            DateTimeUtil.date(settings.getGroupCreationStartDate()),
            DateTimeUtil.date(settings.getGroupCreationEndDate())
        );
    }

    public AuditLogResponse toAuditLogResponse(AuditLog auditLog) {
        return new AuditLogResponse(
            auditLog.getId(),
            auditLog.getAction(),
            auditLog.getEntity(),
            auditLog.getEntityId(),
            auditLog.getAdminEmail(),
            auditLog.getDetails(),
            auditLog.getTimestamp().toString()
        );
    }
}
