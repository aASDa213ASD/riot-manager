package com.riot.manager;
import com.riot.manager.repository.GameAccountRepository;
import com.riot.manager.repository.RegionRepository;
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
public class GameAccountServiceTest {
    @Autowired
    private GameAccountRepository gameAccountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateAccount() throws Exception {
        final String accountToCreate = """
            {
                "regionId": 12,
                "name": "TESTACC",
                "level": 30
            }
        """;

        this.mockMvc.perform(
                        post("/accounts")
                                .contentType(APPLICATION_JSON)
                                .content(accountToCreate)
                ).andDo(print())
                .andExpect(status().isOk());

        assertNotEquals(this.gameAccountRepository.findAll().size(), 2);
    }

    @Test
    void testGetAccById() throws Exception{
        Long longId = Long.parseLong("5");
        this.mockMvc.perform(get("/accounts/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(this.gameAccountRepository.findById(longId), 5);
    }

    @Test
    void testDeleteAccById() throws Exception {
        this.mockMvc.perform(delete("/accounts/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(this.gameAccountRepository.findAll().size(), 2);
    }
}
