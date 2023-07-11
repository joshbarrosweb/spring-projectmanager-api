package com.joshbarrosweb.projectmanager.repositories;

import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find projects by name containing a given keyword.
     *
     * @param name     The keyword to search for in project names.
     * @param pageable The pagination information.
     * @return A page of projects matching the search criteria.
     */
    Page<Project> findByNameContaining(String name, Pageable pageable);

    /**
     * Get the users assigned to a project.
     *
     * @param projectId The ID of the project.
     * @return A list of users assigned to the project.
     */
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.projects p WHERE p.id = :projectId")
    List<User> getUsersByProjectId(@Param("projectId") Long projectId);
}
