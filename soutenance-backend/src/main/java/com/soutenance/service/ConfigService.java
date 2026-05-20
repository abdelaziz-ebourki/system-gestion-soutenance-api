package com.soutenance.service;

import com.soutenance.dto.Dtos.DefenseSettingsDto;
import com.soutenance.dto.Dtos.SimpleNameRequest;
import com.soutenance.dto.Dtos.SimpleNameResponse;
import java.util.List;

public interface ConfigService {
    List<SimpleNameResponse> getFilieres();
    SimpleNameResponse createFiliere(SimpleNameRequest request);
    SimpleNameResponse updateFiliere(String id, SimpleNameRequest request);
    void deleteFiliere(String id);

    List<SimpleNameResponse> getLevels();
    SimpleNameResponse createLevel(SimpleNameRequest request);
    SimpleNameResponse updateLevel(String id, SimpleNameRequest request);
    void deleteLevel(String id);

    List<SimpleNameResponse> getGrades();
    SimpleNameResponse createGrade(SimpleNameRequest request);
    SimpleNameResponse updateGrade(String id, SimpleNameRequest request);
    void deleteGrade(String id);

    DefenseSettingsDto getSettings();
    DefenseSettingsDto updateSettings(DefenseSettingsDto request);

    List<SimpleNameResponse> getSessionTypes();
    SimpleNameResponse createSessionType(SimpleNameRequest request);
    void deleteSessionType(String id);

    List<SimpleNameResponse> getSessionStatuses();
    SimpleNameResponse createSessionStatus(SimpleNameRequest request);
    void deleteSessionStatus(String id);
}
