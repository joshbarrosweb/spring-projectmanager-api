package com.joshbarrosweb.projectmanager.controllers;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Create a new project.
     *
     * @param projectDTO The project data transfer object.
     * @return ResponseEntity containing the created project.
     */
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.createProject(projectDTO));
    }

    /**
     * Get a paginated list of projects.
     *
     * @param pageable The pagination information.
     * @return ResponseEntity containing a page of projects.
     */
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> listProjects(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(projectService.listProjects(pageable));
    }

    /**
     * Get a project by its ID.
     *
     * @param id The ID of the project.
     * @return ResponseEntity containing the project with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    /**
     * Update a project.
     *
     * @param id         The ID of the project to update.
     * @param projectDTO The updated project data transfer object.
     * @return ResponseEntity containing the updated project.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    /**
     * Delete a project.
     *
     * @param id The ID of the project to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search for projects by name.
     *
     * @param name     The name to search for.
     * @param pageable The pagination information.
     * @return ResponseEntity containing a page of projects matching the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDTO>> searchProjects(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(projectService.searchProjects(name, pageable));
    }

    /**
     * Get the list of users associated with a project.
     *
     * @param id The ID of the project.
     * @return ResponseEntity containing the list of users associated with the project.
     */
    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserDTO>> getUsersByProjectId(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getUsersByProjectId(id));
    }
}
