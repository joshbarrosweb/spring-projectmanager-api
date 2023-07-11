package com.joshbarrosweb.projectmanager.controllers;

import com.joshbarrosweb.projectmanager.dtos.UserDTO;
import com.joshbarrosweb.projectmanager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Tests the creation of a user.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void createUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@mail.com");
        when(userService.createUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@mail.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@mail.com"));
    }

    /**
     * Tests the retrieval of a list of users.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void listUsers() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@mail.com");

        Page<UserDTO> users = new PageImpl<>(Arrays.asList(userDTO), PageRequest.of(0, 10), 1);

        when(userService.listUsers(any())).thenReturn(users);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John"))
                .andExpect(jsonPath("$.content[0].email").value("john@mail.com"));
    }

    /**
     * Tests the retrieval of a user by their ID.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void getUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@mail.com");

        when(userService.getUserById(anyLong())).thenReturn(userDTO);

        mockMvc.perform(get("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@mail.com"));
    }

    /**
     * Tests the update of a user.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void updateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@mail.com");

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@mail.com"));
    }

    /**
     * Tests the deletion of a user.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the search for users by name and email.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void searchUsers() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@mail.com");

        Page<UserDTO> users = new PageImpl<>(Arrays.asList(userDTO), PageRequest.of(0, 10), 1);

        when(userService.searchUsers(eq("John"), eq("john@mail.com"), any())).thenReturn(users);

        mockMvc.perform(get("/users/search")
                .param("name", "John")
                .param("email", "john@mail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John"))
                .andExpect(jsonPath("$.content[0].email").value("john@mail.com"));
    }

    /**
     * Tests assigning a user to a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void assignUserToProject() throws Exception {
        mockMvc.perform(post("/users/{userId}/projects/{projectId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests removing a user from a project.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void removeUserFromProject() throws Exception {
        mockMvc.perform(delete("/users/{userId}/projects/{projectId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
