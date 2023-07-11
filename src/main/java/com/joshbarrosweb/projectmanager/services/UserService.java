package com.joshbarrosweb.projectmanager.services;

import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    Page<UserDTO> listUsers(Pageable pageable);
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    Page<UserDTO> searchUsers(String name, String email, Pageable pageable);
    void assignUserToProject(Long userId, Long projectId);
    void removeUserFromProject(Long userId, Long projectId);
}
