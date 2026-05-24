package com.system_gestion_soutenance.api.coordinator.jury.repository;

import com.system_gestion_soutenance.api.coordinator.jury.entity.JuryMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuryMemberRepository extends JpaRepository<JuryMember, String> {
    List<JuryMember> findByTeacher_Id(String teacherId);
    List<JuryMember> findByJuryId(String juryId);
}
