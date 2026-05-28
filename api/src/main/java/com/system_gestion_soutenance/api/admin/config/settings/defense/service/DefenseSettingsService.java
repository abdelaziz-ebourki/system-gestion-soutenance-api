package com.system_gestion_soutenance.api.admin.config.settings.defense.service;

import com.system_gestion_soutenance.api.admin.config.settings.defense.entity.DefenseSettings;
import com.system_gestion_soutenance.api.admin.config.settings.defense.repository.DefenseSettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class DefenseSettingsService {

    private final DefenseSettingsRepository repository;

    public DefenseSettingsService(DefenseSettingsRepository repository) {
        this.repository = repository;
    }

    public DefenseSettings get() {
        return repository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paramètres de soutenance non configurés"));
    }

    public DefenseSettings update(Map<String, Object> updates) {
        DefenseSettings settings = repository.findById(1L)
                .orElse(new DefenseSettings());

        if (updates.containsKey("startTime"))
            settings.setStartTime((String) updates.get("startTime"));
        if (updates.containsKey("endTime"))
            settings.setEndTime((String) updates.get("endTime"));
        if (updates.containsKey("defenseDuration"))
            settings.setDefenseDuration(toInt(updates.get("defenseDuration")));
        if (updates.containsKey("breakDuration"))
            settings.setBreakDuration(toInt(updates.get("breakDuration")));
        if (updates.containsKey("groupCreationStartDate"))
            settings.setGroupCreationStartDate((String) updates.get("groupCreationStartDate"));
        if (updates.containsKey("groupCreationEndDate"))
            settings.setGroupCreationEndDate((String) updates.get("groupCreationEndDate"));

        settings.setId(1L);
        return repository.save(settings);
    }

    private int toInt(Object value) {
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }
}
