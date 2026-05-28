package com.system_gestion_soutenance.api.coordinator.jury.repository;

import com.system_gestion_soutenance.api.coordinator.jury.entity.JuryMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuryMemberRepository extends JpaRepository<JuryMember, Long> {
    List<JuryMember> findByTeacher_Id(Long teacherId);
    List<JuryMember> findByJuryId(Long juryId);
}
