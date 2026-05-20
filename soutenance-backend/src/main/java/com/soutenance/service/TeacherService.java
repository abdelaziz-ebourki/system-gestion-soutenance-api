package com.soutenance.service;

import com.soutenance.dto.Dtos.TeacherDefenseResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationSubmitRequest;
import com.soutenance.dto.Dtos.TeacherStatsDto;
import com.soutenance.dto.Dtos.TeacherUnavailabilityDto;
import java.util.List;

public interface TeacherService {
    TeacherStatsDto getStats();
    List<TeacherDefenseResponse> getSchedule();
    List<TeacherEvaluationResponse> getEvaluations();
    TeacherEvaluationResponse submitEvaluation(String id, TeacherEvaluationSubmitRequest request);
    TeacherUnavailabilityDto getUnavailability();
    TeacherUnavailabilityDto saveUnavailability(TeacherUnavailabilityDto request);
}
