package com.joshbarrosweb.projectmanager.services.impl;

import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import com.joshbarrosweb.projectmanager.repositories.ProjectRepository;
import com.joshbarrosweb.projectmanager.repositories.UserRepository;
import com.joshbarrosweb.projectmanager.services.ConverterService;
import com.joshbarrosweb.projectmanager.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ConverterService converterService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, ConverterService converterService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.converterService = converterService;
    }

    /**
     * Creates a new user.
     *
     * @param userDTO The user DTO containing user information.
     * @return The created user DTO.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = converterService.convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return converterService.convertToDto(savedUser);
    }

    /**
     * Retrieves a page of users.
     *
     * @param pageable The pageable information.
     * @return A page of user DTOs.
     */
    @Override
    public Page<UserDTO> listUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(converterService::convertToDto);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user.
     * @return The user DTO.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return converterService.convertToDto(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id       The ID of the user to update.
     * @param userDTO  The user DTO containing the updated user information.
     * @return The updated user DTO.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        User updatedUser = userRepository.save(user);
        return converterService.convertToDto(updatedUser);
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id The ID of the user to delete.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Searches for users by name and email.
     *
     * @param name     The keyword to search for in user names.
     * @param email    The keyword to search for in user emails.
     * @param pageable The pageable information.
     * @return A page of user DTOs matching the search criteria.
     */
    public Page<UserDTO> searchUsers(String name, String email, Pageable pageable) {
        Page<User> usersByName = userRepository.findByNameContaining(name, pageable);
        Page<User> usersByEmail = userRepository.findByEmail(email, pageable);

        // Combine the lists
        List<User> combined = new ArrayList<>(usersByName.getContent());
        combined.addAll(usersByEmail.getContent());

        // Remove duplicates
        List<User> distinctUsers = combined.stream()
            .distinct()
            .collect(Collectors.toList());

        // Convert to DTOs and return as a page
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), distinctUsers.size());

        return new PageImpl<>(
                distinctUsers.subList(start, end).stream()
                    .map(converterService::convertToDto)
                    .collect(Collectors.toList()),
                pageable,
                distinctUsers.size()
        );
    }

    /**
     * Assigns a user to a project.
     *
     * @param userId    The ID of the user.
     * @param projectId The ID of the project.
     * @throws RuntimeException if the user or project is not found.
     */
        @Override
    @Transactional
    public void assignUserToProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        user.getProjects().add(project);
        project.getUsers().add(user); // Update project's user list

        userRepository.save(user);
        projectRepository.save(project);
    }

    /**
     * Removes a user from a project.
     *
     * @param userId    The ID of the user.
     * @param projectId The ID of the project.
     * @throws RuntimeException if the user or project is not found.
     */
    @Override
    @Transactional
    public void removeUserFromProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        user.getProjects().remove(project);
        project.getUsers().remove(user); // Update project's user list

        userRepository.save(user);
        projectRepository.save(project);
    }
}
