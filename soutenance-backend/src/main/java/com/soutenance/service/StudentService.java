package com.soutenance.service;

import com.soutenance.dto.Dtos.StudentDefenseDetailsDto;
import com.soutenance.dto.Dtos.StudentDocumentResponse;
import com.soutenance.dto.Dtos.StudentGroupDetailsDto;
import com.soutenance.dto.Dtos.StudentGroupWorkspaceDto;
import com.soutenance.dto.Dtos.StudentStatsDto;
import java.util.List;

public interface StudentService {
    StudentStatsDto getStats();
    StudentDefenseDetailsDto getDefense();
    StudentGroupWorkspaceDto getGroupWorkspace();
    StudentGroupDetailsDto createGroup();
    StudentGroupDetailsDto joinGroup(String groupId);
    List<StudentDocumentResponse> getDocuments();
    byte[] getConvocationPdf();
}
