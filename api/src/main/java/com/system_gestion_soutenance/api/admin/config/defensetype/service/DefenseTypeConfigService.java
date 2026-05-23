package com.system_gestion_soutenance.api.admin.config.defensetype.service;

import com.system_gestion_soutenance.api.admin.config.defensetype.entity.DefenseTypeConfig;
import com.system_gestion_soutenance.api.admin.config.defensetype.repository.DefenseTypeConfigRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefenseTypeConfigService {

    private final DefenseTypeConfigRepository repository;

    public DefenseTypeConfigService(DefenseTypeConfigRepository repository) {
        this.repository = repository;
    }

    public Map<String, Map<String, Object>> getAllGrouped() {
        List<DefenseTypeConfig> all = repository.findAll();
        Map<String, Map<String, Object>> result = new HashMap<>();
        for (DefenseTypeConfig dtc : all) {
            Map<String, Object> item = new HashMap<>();
            item.put("enabled", dtc.isEnabled());
            item.put("label", dtc.getLabel());
            item.put("labelPlural", dtc.getLabelPlural());
            item.put("defaultDuration", dtc.getDefaultDuration());
            item.put("defaultBreak", dtc.getDefaultBreak());
            result.put(dtc.getId(), item);
        }
        return result;
    }

    public Map<String, Map<String, Object>> updateGrouped(Map<String, Map<String, Object>> updates) {
        for (Map.Entry<String, Map<String, Object>> entry : updates.entrySet()) {
            String typeId = entry.getKey();
            Map<String, Object> fields = entry.getValue();

            DefenseTypeConfig config = repository.findById(typeId).orElse(null);
            if (config == null) continue;

            if (fields.containsKey("enabled"))
                config.setEnabled((Boolean) fields.get("enabled"));
            if (fields.containsKey("label"))
                config.setLabel((String) fields.get("label"));
            if (fields.containsKey("labelPlural"))
                config.setLabelPlural((String) fields.get("labelPlural"));
            if (fields.containsKey("defaultDuration"))
                config.setDefaultDuration(((Number) fields.get("defaultDuration")).intValue());
            if (fields.containsKey("defaultBreak"))
                config.setDefaultBreak(((Number) fields.get("defaultBreak")).intValue());

            repository.save(config);
        }

        return getAllGrouped();
    }
}
