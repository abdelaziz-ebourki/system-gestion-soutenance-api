package com.soutenance.service;

import com.soutenance.dto.Dtos.CoordinatorStatsDto;
import com.soutenance.dto.Dtos.GroupRequest;
import com.soutenance.dto.Dtos.GroupResponse;
import com.soutenance.dto.Dtos.JuryRequest;
import com.soutenance.dto.Dtos.JuryResponse;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.ProjectRequest;
import com.soutenance.dto.Dtos.ProjectResponse;
import com.soutenance.dto.Dtos.ScheduleRequest;
import java.util.List;

public interface CoordinatorService {
    CoordinatorStatsDto getStats();

    List<ProjectResponse> getProjects();
    ProjectResponse createProject(ProjectRequest request);
    ProjectResponse updateProject(String id, ProjectRequest request);
    void deleteProject(String id);

    List<GroupResponse> getGroups();
    GroupResponse createGroup(GroupRequest request);
    void deleteGroup(String id);

    List<JuryResponse> getJurys();
    JuryResponse createJury(JuryRequest request);
    JuryResponse updateJury(String id, JuryRequest request);
    void deleteJury(String id);

    MessageResponse saveSchedule(ScheduleRequest request);
}
