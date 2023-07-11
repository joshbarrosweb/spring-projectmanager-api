package com.joshbarrosweb.projectmanager.services;

import com.joshbarrosweb.projectmanager.dtos.ProjectDTO;
import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.entities.Project;
import com.joshbarrosweb.projectmanager.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    private final ModelMapper modelMapper;

    public ConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public ProjectDTO convertToDto(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToEntity(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }
}

