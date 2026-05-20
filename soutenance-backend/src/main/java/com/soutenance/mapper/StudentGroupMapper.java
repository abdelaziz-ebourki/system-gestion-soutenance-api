package com.soutenance.mapper;

import com.soutenance.dto.Dtos.AvailableGroupDto;
import com.soutenance.dto.Dtos.GroupResponse;
import com.soutenance.dto.Dtos.StudentGroupDetailsDto;
import com.soutenance.dto.Dtos.StudentGroupMemberDto;
import com.soutenance.entity.Student;
import com.soutenance.entity.StudentGroup;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StudentGroupMapper {

    public GroupResponse toCoordinatorResponse(StudentGroup group) {
        return new GroupResponse(
            group.getId(),
            group.getProject() == null ? null : group.getProject().getId(),
            group.getMembers().stream().map(Student::getId).toList(),
            group.getSession() == null ? null : group.getSession().getId()
        );
    }

    public StudentGroupDetailsDto toStudentDetails(StudentGroup group) {
        List<Student> members = new ArrayList<>(group.getMembers());
        members.sort(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName));
        String leaderId = members.isEmpty() ? null : members.get(0).getId();
        List<StudentGroupMemberDto> memberDtos = members.stream()
            .map(student -> new StudentGroupMemberDto(
                student.getId(),
                student.fullName(),
                student.getEmail(),
                student.getId().equals(leaderId) ? "leader" : "member"
            ))
            .toList();
        return new StudentGroupDetailsDto(
            group.getId(),
            group.getGroupName(),
            group.getProject() == null ? null : group.getProject().getTitle(),
            group.getProject() == null || group.getProject().getSupervisor() == null
                ? null
                : group.getProject().getSupervisor().fullName(),
            memberDtos
        );
    }

    public AvailableGroupDto toAvailableGroup(StudentGroup group) {
        return new AvailableGroupDto(group.getId(), group.getGroupName(), group.getMembers().size());
    }
}
