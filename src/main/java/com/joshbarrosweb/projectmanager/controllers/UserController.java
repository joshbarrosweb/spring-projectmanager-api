package com.joshbarrosweb.projectmanager.controllers;

import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param userDTO The user data transfer object.
     * @return ResponseEntity containing the created user.
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get a paginated list of users.
     *
     * @param pageable The pagination information.
     * @return ResponseEntity containing a page of users.
     */
    @GetMapping
    public ResponseEntity<Page<UserDTO>> listUsers(@PageableDefault(size = 10) Pageable pageable) {
        Page<UserDTO> users = userService.listUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user.
     * @return ResponseEntity containing the user with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Update a user.
     *
     * @param id       The ID of the user to update.
     * @param userDTO  The updated user data transfer object.
     * @return ResponseEntity containing the updated user.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Delete a user.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Search for users by name and email.
     *
     * @param name     The name to search for.
     * @param email    The email to search for.
     * @param pageable The pagination information.
     * @return ResponseEntity containing a page of users matching the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<UserDTO> users = userService.searchUsers(name, email, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Assign a user to a project.
     *
     * @param userId    The ID of the user.
     * @param projectId The ID of the project.
     * @return ResponseEntity indicating the success of the assignment.
     */
    @PostMapping("/{userId}/projects/{projectId}")
    public ResponseEntity<Void> assignUserToProject(@PathVariable Long userId, @PathVariable Long projectId) {
        userService.assignUserToProject(userId, projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Remove a user from a project.
     *
     * @param userId    The ID of the user.
     * @param projectId The ID of the project.
     * @return ResponseEntity indicating the success of the removal.
     */
    @DeleteMapping("/{userId}/projects/{projectId}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable Long userId, @PathVariable Long projectId) {
        userService.removeUserFromProject(userId, projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
