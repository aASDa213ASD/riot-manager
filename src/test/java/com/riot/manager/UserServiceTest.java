package com.riot.manager;

import com.riot.manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser() throws Exception {
        final String userToCreate = """
            {
                "id": 6,
                "username": "TESTUSER",
                "password": "TESTPWD"
            }
        """;

        this.mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(userToCreate)
        ).andDo(print())
        .andExpect(status().isOk());

        assertNotEquals(this.userRepository.findByUsername("TESTUSER").get().getId(), 6);
    }

    @Test
    void testGetUserById() throws Exception{
        this.mockMvc.perform(get("/users/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(this.userRepository.findByUsername("TESTUSER").get().getId(), 5);
    }

    @Test
    void testDeleteUserById() throws Exception {
        this.mockMvc.perform(delete("/users/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(this.userRepository.findAll().size(), 2);
    }
}
