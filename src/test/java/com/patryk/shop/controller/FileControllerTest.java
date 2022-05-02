package com.patryk.shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetJsonFile() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "JSON"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetXlsFile() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "XLS"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPdfFile() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "PDF"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetDocFile() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "DOC"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetCSVFile() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "CSV"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetWrongFileType() throws Exception {

        mockMvc.perform(get("/api/file/generic")
                .queryParam("fileType", "TXT"))
                .andExpect(status().isBadRequest());
    }

}
