package com.joshbarrosweb.projectmanager.controllers;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Tests the creation of a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void createProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        when(projectService.createProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk());
    }

    /**
     * Tests the retrieval of a list of projects.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void listProjects() throws Exception {
        Page<ProjectDTO> projects = new PageImpl<>(Collections.singletonList(new ProjectDTO()), PageRequest.of(0, 10), 1);
        when(projectService.listProjects(any())).thenReturn(projects);

        mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests the retrieval of a project by its ID.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void getProjectById() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        when(projectService.getProjectById(anyLong())).thenReturn(projectDTO);

        mockMvc.perform(get("/projects/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests the update of a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void updateProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        when(projectService.updateProject(anyLong(), any(ProjectDTO.class))).thenReturn(projectDTO);

        mockMvc.perform(put("/projects/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk());
    }

    /**
     * Tests the deletion of a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void deleteProject() throws Exception {
        mockMvc.perform(delete("/projects/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the search for projects by name.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void searchProjects() throws Exception {
        Page<ProjectDTO> projects = new PageImpl<>(Collections.singletonList(new ProjectDTO()), PageRequest.of(0, 10), 1);
        when(projectService.searchProjects(anyString(), any())).thenReturn(projects);

        mockMvc.perform(get("/projects/search")
                .param("name", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests the retrieval of users assigned to a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void getUsersByProjectId() throws Exception {
        when(projectService.getUsersByProjectId(anyLong())).thenReturn(Arrays.asList(new UserDTO()));

        mockMvc.perform(get("/projects/{id}/users", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
