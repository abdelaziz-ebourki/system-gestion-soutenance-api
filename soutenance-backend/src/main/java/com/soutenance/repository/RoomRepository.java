package com.soutenance.repository;

import com.soutenance.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
    boolean existsByNameIgnoreCase(String name);
}
