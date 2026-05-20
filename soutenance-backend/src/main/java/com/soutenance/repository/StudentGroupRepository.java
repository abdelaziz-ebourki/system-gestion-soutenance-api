package com.soutenance.repository;

import com.soutenance.entity.StudentGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, String> {
    @Query("select g from StudentGroup g join g.members m where m.id = :studentId")
    Optional<StudentGroup> findByMemberId(@Param("studentId") String studentId);

    @Query("select g from StudentGroup g where not exists (select m from g.members m where m.id = :studentId)")
    List<StudentGroup> findAvailableForStudent(@Param("studentId") String studentId);
}
