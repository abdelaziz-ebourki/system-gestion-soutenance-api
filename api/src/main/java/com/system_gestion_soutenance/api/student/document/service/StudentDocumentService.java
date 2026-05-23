package com.system_gestion_soutenance.api.student.document.service;

import com.system_gestion_soutenance.api.student.document.entity.StudentDocument;
import com.system_gestion_soutenance.api.student.document.repository.StudentDocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentDocumentService {

    private final StudentDocumentRepository repository;
    private final Path uploadDir = Paths.get("uploads");

    public StudentDocumentService(StudentDocumentRepository repository) {
        this.repository = repository;
    }

    public List<StudentDocument> findByStudent(String studentId) {
        return repository.findByStudentId(studentId);
    }

    public StudentDocument upload(String id, MultipartFile file) {
        StudentDocument doc = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Document non trouvé"));

        try {
            Files.createDirectories(uploadDir);
            String filename = id + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            file.transferTo(target.toFile());

            doc.setFilePath(target.toString());
            doc.setSubmittedAt(LocalDateTime.now());
            doc.setStatus("submitted");
            return repository.save(doc);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors du téléchargement du fichier");
        }
    }
}
