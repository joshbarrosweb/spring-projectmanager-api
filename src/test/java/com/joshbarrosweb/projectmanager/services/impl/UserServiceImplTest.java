package com.joshbarrosweb.projectmanager.services.impl;

import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import com.joshbarrosweb.projectmanager.repositories.ProjectRepository;
import com.joshbarrosweb.projectmanager.repositories.UserRepository;
import com.joshbarrosweb.projectmanager.services.ConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ConverterService converterService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        projectRepository = mock(ProjectRepository.class);
        converterService = mock(ConverterService.class);
        userService = new UserServiceImpl(userRepository, projectRepository, converterService);
    }

    /**
     * Tests the createUser() method of the UserServiceImpl class.
     * It verifies that a UserDTO is created and saved successfully.
     */
    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(converterService.convertToEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(converterService.convertToDto(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(userDTO, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the listUsers() method of the UserServiceImpl class.
     * It verifies that the list of users is retrieved successfully.
     */
    @Test
    void listUsers() {
        Page<User> users = new PageImpl<>(Collections.singletonList(new User()), PageRequest.of(0, 10), 1);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(users);

        userService.listUsers(PageRequest.of(0, 10));

        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    /**
     * Tests the getUserById() method of the UserServiceImpl class.
     * It verifies that a user with the specified ID is retrieved successfully.
     */
    @Test
    void getUserById() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.getUserById(1L);

        verify(userRepository, times(1)).findById(anyLong());
    }

    /**
     * Tests the updateUser() method of the UserServiceImpl class.
     * It verifies that a user is updated successfully with the given ID and UserDTO.
     */
    @Test
    void updateUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(1L, userDTO);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the deleteUser() method of the UserServiceImpl class.
     * It verifies that a user with the specified ID is deleted successfully.
     */
    @Test
    void deleteUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    /**
     * Tests the searchUsers() method of the UserServiceImpl class.
     * It verifies that users can be searched successfully by name and email.
     */
    @Test
    void searchUsers() {
        Page<User> users = new PageImpl<>(Collections.singletonList(new User()), PageRequest.of(0, 10), 1);
        when(userRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(users);
        when(userRepository.findByEmail(anyString(), any(Pageable.class))).thenReturn(users);

        userService.searchUsers("test", "test@test.com", PageRequest.of(0, 10));

        verify(userRepository, times(1)).findByNameContaining(anyString(), any(Pageable.class));
        verify(userRepository, times(1)).findByEmail(anyString(), any(Pageable.class));
    }

    /* 

    /**
      * Test case for assigning a user to a project.
      * It verifies that a user can be assigned to a project
      * by adding the project to the user's project set and saving
      * the user in the repository.
     */
    
    /*
    @Test
    void assignUserToProject() {
        User user = new User();
        user.setProjects(Set.of(new Project()));
        Project project = new Project();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        userService.assignUserToProject(1L, 1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }
    */


    /**
      * Test case for removing a user to a project.
      * It verifies that a user can be removed to a project
      * by removing the user in the project.
    */
    
    /*
    @Test
    void removeUserFromProject() {

        User user = new User();
        Project project = new Project();
        user.setProjects(Set.of(project));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        userService.removeUserFromProject(1L, 1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    } */
}