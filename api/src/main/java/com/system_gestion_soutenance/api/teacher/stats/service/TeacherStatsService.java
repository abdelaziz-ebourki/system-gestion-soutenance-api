package com.system_gestion_soutenance.api.teacher.stats.service;

import com.system_gestion_soutenance.api.coordinator.jury.repository.JuryRepository;
import com.system_gestion_soutenance.api.coordinator.unavailability.repository.UnavailabilityRepository;
import com.system_gestion_soutenance.api.teacher.evaluation.repository.EvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherStatsService {

    private final EvaluationRepository evaluationRepository;
    private final JuryRepository juryRepository;
    private final UnavailabilityRepository unavailabilityRepository;

    public TeacherStatsService(EvaluationRepository evaluationRepository,
                                JuryRepository juryRepository,
                                UnavailabilityRepository unavailabilityRepository) {
        this.evaluationRepository = evaluationRepository;
        this.juryRepository = juryRepository;
        this.unavailabilityRepository = unavailabilityRepository;
    }

    public Map<String, Object> getStats(String teacherId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("upcomingDefenses", 0);
        stats.put("pendingEvaluations", evaluationRepository.findByTeacherId(teacherId).stream()
                .filter(e -> "pending".equals(e.getStatus())).count());
        stats.put("declaredUnavailabilitySlots",
                unavailabilityRepository.findAll().stream()
                        .filter(u -> u.getTeacherId().equals(teacherId))
                        .mapToLong(u -> u.getSlots() != null ? u.getSlots().size() : 0)
                        .sum());
        stats.put("juryAssignments", juryRepository.findAll().stream()
                .filter(j -> j.getPresident().getId().equals(teacherId)
                        || j.getReporter().getId().equals(teacherId)
                        || j.getExaminer().getId().equals(teacherId))
                .count());
        return stats;
    }
}
