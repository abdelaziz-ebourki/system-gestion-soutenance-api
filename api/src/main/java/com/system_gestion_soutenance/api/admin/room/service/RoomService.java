package com.system_gestion_soutenance.api.admin.room.service;

import com.system_gestion_soutenance.api.admin.department.entity.Department;
import com.system_gestion_soutenance.api.admin.department.repository.DepartmentRepository;
import com.system_gestion_soutenance.api.admin.room.dto.BulkRoomRequest;
import com.system_gestion_soutenance.api.admin.room.dto.CreateRoomRequest;
import com.system_gestion_soutenance.api.admin.room.entity.Room;
import com.system_gestion_soutenance.api.admin.room.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final DepartmentRepository departmentRepository;

    public RoomService(RoomRepository roomRepository, DepartmentRepository departmentRepository) {
        this.roomRepository = roomRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room create(CreateRoomRequest request) {
        Department dept = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Département introuvable"));

        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        room.setName(request.name());
        room.setCapacity(request.capacity());
        room.setDepartment(dept);
        return roomRepository.save(room);
    }

    public List<Room> bulkCreate(BulkRoomRequest request) {
        List<Room> created = new ArrayList<>();

        for (BulkRoomRequest.RoomEntry entry : request.rooms()) {
            Department dept = departmentRepository.findById(entry.departmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Département introuvable: " + entry.departmentId()));

            Room room = new Room();
            room.setId(UUID.randomUUID().toString());
            room.setName(entry.name());
            room.setCapacity(entry.capacity());
            room.setDepartment(dept);
            created.add(roomRepository.save(room));
        }

        return created;
    }

    public Room update(String id, CreateRoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Salle non trouvée"));

        Department dept = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Département introuvable"));

        room.setName(request.name());
        room.setCapacity(request.capacity());
        room.setDepartment(dept);
        return roomRepository.save(room);
    }

    public void delete(String id) {
        if (!roomRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salle non trouvée");
        }
        roomRepository.deleteById(id);
    }
}
