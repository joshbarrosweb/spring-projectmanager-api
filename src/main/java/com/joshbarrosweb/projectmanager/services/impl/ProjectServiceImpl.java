package com.joshbarrosweb.projectmanager.services.impl;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import com.joshbarrosweb.projectmanager.repositories.ProjectRepository;
import com.joshbarrosweb.projectmanager.services.ConverterService;
import com.joshbarrosweb.projectmanager.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ConverterService converterService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ConverterService converterService) {
        this.projectRepository = projectRepository;
        this.converterService = converterService;
    }

    /**
     * Creates a new project.
     *
     * @param projectDTO The project DTO containing project information.
     * @return The created project DTO.
     */
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = converterService.convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return converterService.convertToDto(savedProject);
    }

    /**
     * Retrieves a page of projects.
     *
     * @param pageable The pageable information.
     * @return A page of project DTOs.
     */
    @Override
    public Page<ProjectDTO> listProjects(Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);
        return projects.map(converterService::convertToDto);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id The ID of the project.
     * @return The project DTO.
     * @throws RuntimeException if the project is not found.
     */
    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return converterService.convertToDto(project);
    }

    /**
     * Updates an existing project.
     *
     * @param id         The ID of the project to update.
     * @param projectDTO The project DTO containing the updated project information.
     * @return The updated project DTO.
     * @throws RuntimeException if the project is not found.
     */
    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        Project updatedProject = projectRepository.save(project);
        return converterService.convertToDto(updatedProject);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id The ID of the project to delete.
     * @throws RuntimeException if the project is not found.
     */
    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    /**
     * Searches for projects by name containing a given keyword.
     *
     * @param name     The keyword to search for in project names.
     * @param pageable The pageable information.
     * @return A page of project DTOs matching the search criteria.
     */
    @Override
    public Page<ProjectDTO> searchProjects(String name, Pageable pageable) {
        Page<Project> projects = projectRepository.findByNameContaining(name, pageable);
        return projects.map(converterService::convertToDto);
    }

    /**
     * Retrieves a list of users associated with a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of user DTOs associated with the project.
     */
    @Override
    public List<UserDTO> getUsersByProjectId(Long projectId) {
        List<User> users = projectRepository.getUsersByProjectId(projectId);
        return users.stream().map(converterService::convertToDto).collect(Collectors.toList());
    }
}
