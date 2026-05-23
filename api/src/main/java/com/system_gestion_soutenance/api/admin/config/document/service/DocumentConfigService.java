package com.system_gestion_soutenance.api.admin.config.document.service;

import com.system_gestion_soutenance.api.admin.config.document.entity.DocumentConfig;
import com.system_gestion_soutenance.api.admin.config.document.repository.DocumentConfigRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class DocumentConfigService {

    private final DocumentConfigRepository repository;

    public DocumentConfigService(DocumentConfigRepository repository) {
        this.repository = repository;
    }

    public DocumentConfig get() {
        return repository.findById("default")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Configuration des documents non trouvée"));
    }

    public DocumentConfig update(Map<String, Object> updates) {
        DocumentConfig config = repository.findById("default")
                .orElse(new DocumentConfig());

        if (updates.containsKey("maxFileSizeMb"))
            config.setMaxFileSizeMb(toInt(updates.get("maxFileSizeMb")));
        if (updates.containsKey("allowedExtensions"))
            config.setAllowedExtensions((String) updates.get("allowedExtensions"));
        if (updates.containsKey("versionLimit"))
            config.setVersionLimit(toInt(updates.get("versionLimit")));

        config.setId("default");
        return repository.save(config);
    }

    private int toInt(Object value) {
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }
}
