package com.joshbarrosweb.projectmanager.services.impl;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import com.joshbarrosweb.projectmanager.repositories.ProjectRepository;
import com.joshbarrosweb.projectmanager.services.ConverterService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProjectServiceImplTest {

    private ProjectServiceImpl projectService;
    private ProjectRepository projectRepository;
    private ConverterService converterService;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        converterService = mock(ConverterService.class);
        projectService = new ProjectServiceImpl(projectRepository, converterService);
    }

    /**
     * Tests the creation of a project.
     */
    @Test
    void createProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();
        when(converterService.convertToEntity(any(ProjectDTO.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(converterService.convertToDto(any(Project.class))).thenReturn(projectDTO);

        ProjectDTO result = projectService.createProject(projectDTO);

        assertEquals(projectDTO, result);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    /**
     * Tests the retrieval of a list of projects.
     */
    @Test
    void listProjects() {
        Page<Project> projects = new PageImpl<>(Collections.singletonList(new Project()), PageRequest.of(0, 10), 1);
        when(projectRepository.findAll(any(PageRequest.class))).thenReturn(projects);

        projectService.listProjects(PageRequest.of(0, 10));

        verify(projectRepository, times(1)).findAll(any(PageRequest.class));
    }

    /**
     * Tests the retrieval of a project by its ID.
     */
    @Test
    void getProjectById() {
        Project project = new Project();
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        projectService.getProjectById(1L);

        verify(projectRepository, times(1)).findById(anyLong());
    }

    /**
     * Tests the update of a project.
     */
    @Test
    void updateProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.updateProject(1L, projectDTO);

        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    /**
     * Tests the deletion of a project.
     */
    @Test
    void deleteProject() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).existsById(anyLong());
        verify(projectRepository, times(1)).deleteById(anyLong());
    }

    /**
     * Tests the search for projects by name.
     */
    @Test
    void searchProjects() {
        Page<Project> projects = new PageImpl<>(Collections.singletonList(new Project()), PageRequest.of(0, 10), 1);
        when(projectRepository.findByNameContaining(anyString(), any(PageRequest.class))).thenReturn(projects);

        projectService.searchProjects("test", PageRequest.of(0, 10));

        verify(projectRepository, times(1)).findByNameContaining(anyString(), any(PageRequest.class));
    }

    /**
     * Tests the retrieval of users associated with a project.
     */
    @Test
    void getUsersByProjectId() {
        when(projectRepository.getUsersByProjectId(anyLong())).thenReturn(Collections.singletonList(new User()));

        projectService.getUsersByProjectId(1L);

        verify(projectRepository, times(1)).getUsersByProjectId(anyLong());
    }
}
