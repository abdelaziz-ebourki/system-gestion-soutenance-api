package com.soutenance.service.impl;

import com.soutenance.dto.Dtos.DefenseSettingsDto;
import com.soutenance.dto.Dtos.SimpleNameRequest;
import com.soutenance.dto.Dtos.SimpleNameResponse;
import com.soutenance.entity.ConfigOption;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Filiere;
import com.soutenance.entity.Grade;
import com.soutenance.entity.Level;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.AcademicMapper;
import com.soutenance.repository.ConfigOptionRepository;
import com.soutenance.repository.DefenseSettingsRepository;
import com.soutenance.repository.FiliereRepository;
import com.soutenance.repository.GradeRepository;
import com.soutenance.repository.LevelRepository;
import com.soutenance.service.ConfigService;
import com.soutenance.util.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private static final String SETTINGS_ID = "default-settings";
    private static final String SESSION_TYPE = "session-type";
    private static final String SESSION_STATUS = "session-status";

    private final FiliereRepository filiereRepository;
    private final LevelRepository levelRepository;
    private final GradeRepository gradeRepository;
    private final DefenseSettingsRepository defenseSettingsRepository;
    private final ConfigOptionRepository configOptionRepository;
    private final AcademicMapper academicMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getFilieres() {
        return filiereRepository.findAll(Sort.by("name")).stream().map(academicMapper::toSimpleResponse).toList();
    }

    @Override
    @Transactional
    public SimpleNameResponse createFiliere(SimpleNameRequest request) {
        Filiere filiere = new Filiere();
        filiere.setName(request.name());
        return academicMapper.toSimpleResponse(filiereRepository.save(filiere));
    }

    @Override
    @Transactional
    public SimpleNameResponse updateFiliere(String id, SimpleNameRequest request) {
        Filiere filiere = filiereRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Filiere introuvable"));
        filiere.setName(request.name());
        return academicMapper.toSimpleResponse(filiereRepository.save(filiere));
    }

    @Override
    @Transactional
    public void deleteFiliere(String id) {
        filiereRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getLevels() {
        return levelRepository.findAll(Sort.by("name")).stream().map(academicMapper::toSimpleResponse).toList();
    }

    @Override
    @Transactional
    public SimpleNameResponse createLevel(SimpleNameRequest request) {
        Level level = new Level();
        level.setName(request.name());
        return academicMapper.toSimpleResponse(levelRepository.save(level));
    }

    @Override
    @Transactional
    public SimpleNameResponse updateLevel(String id, SimpleNameRequest request) {
        Level level = levelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Niveau introuvable"));
        level.setName(request.name());
        return academicMapper.toSimpleResponse(levelRepository.save(level));
    }

    @Override
    @Transactional
    public void deleteLevel(String id) {
        levelRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getGrades() {
        return gradeRepository.findAll(Sort.by("name")).stream().map(academicMapper::toSimpleResponse).toList();
    }

    @Override
    @Transactional
    public SimpleNameResponse createGrade(SimpleNameRequest request) {
        Grade grade = new Grade();
        grade.setName(request.name());
        return academicMapper.toSimpleResponse(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public SimpleNameResponse updateGrade(String id, SimpleNameRequest request) {
        Grade grade = gradeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Grade introuvable"));
        grade.setName(request.name());
        return academicMapper.toSimpleResponse(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public void deleteGrade(String id) {
        gradeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DefenseSettingsDto getSettings() {
        return academicMapper.toSettingsResponse(findOrCreateSettings());
    }

    @Override
    @Transactional
    public DefenseSettingsDto updateSettings(DefenseSettingsDto request) {
        DefenseSettings settings = findOrCreateSettings();
        settings.setStartTime(DateTimeUtil.parseTime(request.startTime()));
        settings.setEndTime(DateTimeUtil.parseTime(request.endTime()));
        settings.setDefenseDuration(request.defenseDuration());
        settings.setBreakDuration(request.breakDuration());
        settings.setGroupCreationStartDate(DateTimeUtil.parseDate(request.groupCreationStartDate()));
        settings.setGroupCreationEndDate(DateTimeUtil.parseDate(request.groupCreationEndDate()));
        return academicMapper.toSettingsResponse(defenseSettingsRepository.save(settings));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getSessionTypes() {
        return options(SESSION_TYPE);
    }

    @Override
    @Transactional
    public SimpleNameResponse createSessionType(SimpleNameRequest request) {
        return createOption(SESSION_TYPE, request.name());
    }

    @Override
    @Transactional
    public void deleteSessionType(String id) {
        configOptionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleNameResponse> getSessionStatuses() {
        return options(SESSION_STATUS);
    }

    @Override
    @Transactional
    public SimpleNameResponse createSessionStatus(SimpleNameRequest request) {
        return createOption(SESSION_STATUS, request.name());
    }

    @Override
    @Transactional
    public void deleteSessionStatus(String id) {
        configOptionRepository.deleteById(id);
    }

    private DefenseSettings findOrCreateSettings() {
        return defenseSettingsRepository.findById(SETTINGS_ID).orElseGet(() -> {
            DefenseSettings settings = new DefenseSettings();
            settings.setId(SETTINGS_ID);
            settings.setStartTime(LocalTime.of(8, 0));
            settings.setEndTime(LocalTime.of(18, 0));
            settings.setDefenseDuration(30);
            settings.setBreakDuration(15);
            settings.setGroupCreationStartDate(LocalDate.of(2026, 5, 1));
            settings.setGroupCreationEndDate(LocalDate.of(2026, 6, 20));
            return defenseSettingsRepository.save(settings);
        });
    }

    private List<SimpleNameResponse> options(String category) {
        return configOptionRepository.findByCategoryOrderByNameAsc(category).stream()
            .map(option -> new SimpleNameResponse(option.getId(), option.getName()))
            .toList();
    }

    private SimpleNameResponse createOption(String category, String name) {
        ConfigOption option = new ConfigOption();
        option.setCategory(category);
        option.setName(name);
        ConfigOption saved = configOptionRepository.save(option);
        return new SimpleNameResponse(saved.getId(), saved.getName());
    }
}
