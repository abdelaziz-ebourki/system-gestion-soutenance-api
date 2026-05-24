package com.system_gestion_soutenance.api.admin.department.service;

import com.system_gestion_soutenance.api.admin.department.dto.CreateDepartmentRequest;
import com.system_gestion_soutenance.api.admin.department.entity.Department;
import com.system_gestion_soutenance.api.admin.department.repository.DepartmentRepository;
import com.system_gestion_soutenance.api.admin.room.repository.RoomRepository;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import com.system_gestion_soutenance.api.user.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                              TeacherRepository teacherRepository,
                              RoomRepository roomRepository) {
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(String id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Département non trouvé"));
    }

    public Department create(CreateDepartmentRequest request) {
        if (departmentRepository.findByName(request.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un département avec ce nom existe déjà");
        }

        Department department = new Department();
        department.setId(UUID.randomUUID().toString());
        department.setName(request.name());
        department.setCode(request.code());

        if (request.headId() != null && !request.headId().isBlank()) {
            Teacher head = teacherRepository.findById(request.headId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Enseignant responsable introuvable"));
            department.setHead(head);
        }

        return departmentRepository.save(department);
    }

    public Department update(String id, CreateDepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Département non trouvé"));

        department.setName(request.name());
        department.setCode(request.code());

        if (request.headId() != null && !request.headId().isBlank()) {
            Teacher head = teacherRepository.findById(request.headId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Enseignant responsable introuvable"));
            department.setHead(head);
        } else {
            department.setHead(null);
        }

        return departmentRepository.save(department);
    }

    public void delete(String id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Département non trouvé"));

        if (!teacherRepository.findByDepartmentId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer ce département car des enseignants y sont rattachés");
        }

        if (!roomRepository.findByDepartment_Id(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer ce département car des salles y sont rattachées");
        }

        departmentRepository.delete(department);
    }
}
