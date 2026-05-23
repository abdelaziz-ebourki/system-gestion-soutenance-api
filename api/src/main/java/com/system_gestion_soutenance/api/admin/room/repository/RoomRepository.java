package com.system_gestion_soutenance.api.admin.room.repository;

import com.system_gestion_soutenance.api.admin.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByDepartmentId(String departmentId);
}
