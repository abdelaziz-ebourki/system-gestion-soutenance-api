package com.soutenance.repository;

import com.soutenance.entity.TeacherUnavailability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherUnavailabilityRepository extends JpaRepository<TeacherUnavailability, String> {
    List<TeacherUnavailability> findByTeacherIdOrderByDateAscStartTimeAsc(String teacherId);
    long countByTeacherId(String teacherId);
    void deleteByTeacherId(String teacherId);
}
