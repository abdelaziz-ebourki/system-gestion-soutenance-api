package com.soutenance.mapper;

import com.soutenance.dto.Dtos.JuryResponse;
import com.soutenance.entity.Jury;
import com.soutenance.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class JuryMapper {

    public JuryResponse toResponse(Jury jury) {
        return new JuryResponse(
            jury.getId(),
            jury.getProject().getId(),
            jury.getProject().getTitle(),
            jury.getPresident().getId(),
            name(jury.getPresident()),
            jury.getReporter().getId(),
            name(jury.getReporter()),
            jury.getExaminer().getId(),
            name(jury.getExaminer())
        );
    }

    private String name(Teacher teacher) {
        return teacher == null ? "" : teacher.fullName();
    }
}
