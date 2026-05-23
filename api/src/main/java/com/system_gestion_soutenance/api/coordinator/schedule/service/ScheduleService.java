package com.system_gestion_soutenance.api.coordinator.schedule.service;

import com.system_gestion_soutenance.api.admin.room.entity.Room;
import com.system_gestion_soutenance.api.admin.room.repository.RoomRepository;
import com.system_gestion_soutenance.api.coordinator.schedule.entity.SlotAssignment;
import com.system_gestion_soutenance.api.coordinator.schedule.repository.SlotAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final SlotAssignmentRepository slotAssignmentRepository;
    private final RoomRepository roomRepository;

    public ScheduleService(SlotAssignmentRepository slotAssignmentRepository,
                            RoomRepository roomRepository) {
        this.slotAssignmentRepository = slotAssignmentRepository;
        this.roomRepository = roomRepository;
    }

    public Map<String, Map<String, Object>> getSchedule() {
        List<SlotAssignment> slots = slotAssignmentRepository.findAll();
        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        for (SlotAssignment slot : slots) {
            result.put(slot.getId(), toResponse(slot));
        }
        return result;
    }

    public Map<String, Map<String, Object>> saveSchedule(Map<String, Map<String, Object>> schedule) {
        slotAssignmentRepository.deleteAll();

        for (Map.Entry<String, Map<String, Object>> entry : schedule.entrySet()) {
            String slotId = entry.getKey();
            Map<String, Object> data = entry.getValue();

            SlotAssignment slot = new SlotAssignment();
            slot.setId(slotId);
            slot.setTitle((String) data.get("title"));
            slot.setDate((String) data.get("date"));
            slot.setTime((String) data.get("time"));

            if (data.containsKey("projectId") && data.get("projectId") != null)
                slot.setProjectId((String) data.get("projectId"));

            if (data.containsKey("roomId") && data.get("roomId") != null) {
                Room room = roomRepository.findById((String) data.get("roomId"))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Salle introuvable: " + data.get("roomId")));
                slot.setRoom(room);
            }

            slotAssignmentRepository.save(slot);
        }

        return getSchedule();
    }

    private Map<String, Object> toResponse(SlotAssignment slot) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", slot.getId());
        map.put("title", slot.getTitle());
        map.put("date", slot.getDate());
        map.put("time", slot.getTime());
        map.put("projectId", slot.getProjectId());
        map.put("roomId", slot.getRoom() != null ? slot.getRoom().getId() : null);
        return map;
    }
}
