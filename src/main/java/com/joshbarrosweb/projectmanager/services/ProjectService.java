package com.joshbarrosweb.projectmanager.services;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    Page<ProjectDTO> listProjects(Pageable pageable);
    ProjectDTO getProjectById(Long id);
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);
    void deleteProject(Long id);
    Page<ProjectDTO> searchProjects(String name, Pageable pageable);
    List<UserDTO> getUsersByProjectId(Long projectId);
}
