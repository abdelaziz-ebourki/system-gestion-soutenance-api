package com.system_gestion_soutenance.api.admin.config.general.service;

import com.system_gestion_soutenance.api.admin.config.general.entity.GeneralSettings;
import com.system_gestion_soutenance.api.admin.config.general.repository.GeneralSettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class GeneralSettingsService {

    private final GeneralSettingsRepository repository;

    public GeneralSettingsService(GeneralSettingsRepository repository) {
        this.repository = repository;
    }

    public GeneralSettings get() {
        return repository.findById("default")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paramètres généraux non configurés"));
    }

    public GeneralSettings update(Map<String, Object> updates) {
        GeneralSettings settings = repository.findById("default")
                .orElse(new GeneralSettings());

        if (updates.containsKey("institutionName"))
            settings.setInstitutionName((String) updates.get("institutionName"));
        if (updates.containsKey("institutionLogoUrl"))
            settings.setInstitutionLogoUrl((String) updates.get("institutionLogoUrl"));
        if (updates.containsKey("timezone"))
            settings.setTimezone((String) updates.get("timezone"));
        if (updates.containsKey("dateFormat"))
            settings.setDateFormat((String) updates.get("dateFormat"));

        settings.setId("default");
        return repository.save(settings);
    }
}
