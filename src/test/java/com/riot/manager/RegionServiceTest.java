package com.riot.manager;
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
public class RegionServiceTest {
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateRegion() throws Exception {
        final String regionToCreate = """
            {
                "regionName": "KR",
                "userId": 3
            }
        """;

        this.mockMvc.perform(
                        post("/regions")
                                .contentType(APPLICATION_JSON)
                                .content(regionToCreate)
                ).andDo(print())
                .andExpect(status().isOk());

        assertNotEquals(this.regionRepository.findByRegionName("KR").get().getId(), 12);
    }

    @Test
    void testGetRegionById() throws Exception{
        this.mockMvc.perform(get("/regions/{id}", 12))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(this.regionRepository.findByRegionName("EUW").get().getId(), 1);
    }

    @Test
    void testDeleteUserById() throws Exception {
        this.mockMvc.perform(delete("/regions/{id}", 13))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(this.regionRepository.findAll().size(), 2);
    }
}
