package com.system_gestion_soutenance.api.admin.room.controller;

import com.system_gestion_soutenance.api.admin.room.dto.BulkRoomRequest;
import com.system_gestion_soutenance.api.admin.room.dto.CreateRoomRequest;
import com.system_gestion_soutenance.api.admin.room.entity.Room;
import com.system_gestion_soutenance.api.admin.room.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping
    public ResponseEntity<Room> create(@Valid @RequestBody CreateRoomRequest request) {
        Room room = roomService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Room>> bulkCreate(@Valid @RequestBody BulkRoomRequest request) {
        List<Room> rooms = roomService.bulkCreate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(rooms);
    }

    @PutMapping("/{id}")
    public Room update(@PathVariable String id, @Valid @RequestBody CreateRoomRequest request) {
        return roomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
