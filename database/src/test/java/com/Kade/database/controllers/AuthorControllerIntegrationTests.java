package com.Kade.database.controllers;

import com.Kade.database.TestDataUtil;
import com.Kade.database.domain.dto.AuthorDto;
import com.Kade.database.domain.entities.AuthorEntity;
import com.Kade.database.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHTTP201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthorA); // <-- store the JSON string

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthorA); // <-- store the JSON string

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception{
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }


    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorsReturnsHttpStatus404WhenNoAuthorsExist() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/00")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorsReturnsHttpStatus404WhenNoAuthorsExist() throws Exception{
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson  = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorsReturnsHttpStatus200WhenNoAuthorsExist() throws Exception{
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson  = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId() )
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdatesExistingAuthor() throws Exception{
        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityB);

        AuthorEntity authorDto = TestDataUtil.createTestAuthorB();
        authorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId() )
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(44)
        );
    }
    @Test
    public void testThatGetAuthorsReturnAuthorWhenAuthorsExist() throws Exception{
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus20Ok() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDtoA.getAge())
        );
    }
    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/999")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
