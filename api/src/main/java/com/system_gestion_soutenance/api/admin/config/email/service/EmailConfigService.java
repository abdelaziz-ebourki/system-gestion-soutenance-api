package com.system_gestion_soutenance.api.admin.config.email.service;

import com.system_gestion_soutenance.api.admin.config.email.entity.EmailConfig;
import com.system_gestion_soutenance.api.admin.config.email.repository.EmailConfigRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class EmailConfigService {

    private final EmailConfigRepository repository;

    public EmailConfigService(EmailConfigRepository repository) {
        this.repository = repository;
    }

    public EmailConfig get() {
        return repository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Configuration email non trouvée"));
    }

    public EmailConfig update(Map<String, Object> updates) {
        EmailConfig config = repository.findById(1L)
                .orElse(new EmailConfig());

        if (updates.containsKey("host"))
            config.setHost((String) updates.get("host"));
        if (updates.containsKey("port"))
            config.setPort(toInt(updates.get("port")));
        if (updates.containsKey("username"))
            config.setUsername((String) updates.get("username"));
        if (updates.containsKey("password"))
            config.setPassword((String) updates.get("password"));
        if (updates.containsKey("senderName"))
            config.setSenderName((String) updates.get("senderName"));
        if (updates.containsKey("senderEmail"))
            config.setSenderEmail((String) updates.get("senderEmail"));
        if (updates.containsKey("encryption"))
            config.setEncryption((String) updates.get("encryption"));

        config.setId(1L);
        return repository.save(config);
    }

    private int toInt(Object value) {
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }
}
