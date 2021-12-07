package ru.lanit.web.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import ru.lanit.web.AbstractTestClass;
import ru.lanit.web.WebApplication;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-for-personwithcars.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PersonWithCarsControllerTest extends AbstractTestClass {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonWithCarsController personWithCarsController;

    @MockBean
    BindingResult bindingResult;

    @Test
    public void getPersonWithCarReturnIsOk() throws Exception {
        mockMvc.perform(get("/personwithcars")
                        .param("personid", "4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("validperson"))
                .andExpect(jsonPath("$.birthdate").value("10.10.2000"))
                .andExpect(jsonPath("$.cars").isArray())
                .andExpect(jsonPath("$.cars", hasSize(1)))
                .andExpect(jsonPath("$.cars[0].id").value(6))
                .andExpect(jsonPath("$.cars[0].model").value("VAZ-2105"))
                .andExpect(jsonPath("$.cars[0].horsepower").value(70))
                .andExpect(jsonPath("$.cars[0].ownerId").value(4));
    }

    @Test
    public void getPersonWithCarsReturnIsOk() throws Exception {
        mockMvc.perform(get("/personwithcars")
                        .param("personid", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Olya"))
                .andExpect(jsonPath("$.birthdate").value("12.12.2000"))
                .andExpect(jsonPath("$.cars").isArray())
                .andExpect(jsonPath("$.cars", hasSize(3)))
                .andExpect(jsonPath("$.cars[0].id").value(1))
                .andExpect(jsonPath("$.cars[0].model").value("BMW-525d"))
                .andExpect(jsonPath("$.cars[0].horsepower").value(190))
                .andExpect(jsonPath("$.cars[0].ownerId").value(1))
                .andExpect(jsonPath("$.cars[1].id").value(2))
                .andExpect(jsonPath("$.cars[1].model").value("KIA-Sportage"))
                .andExpect(jsonPath("$.cars[1].horsepower").value(184))
                .andExpect(jsonPath("$.cars[1].ownerId").value(1))
                .andExpect(jsonPath("$.cars[2].id").value(5))
                .andExpect(jsonPath("$.cars[2].model").value("Volkswagen-Tiguan"))
                .andExpect(jsonPath("$.cars[2].horsepower").value(150))
                .andExpect(jsonPath("$.cars[2].ownerId").value(1));

    }

    @Test
    public void getPersonWithCarsThrowNotFound() throws Exception {
        mockMvc.perform(get("/personwithcars")
                        .param("personid", "999999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPersonWithCarsThrowBadRequest() throws Exception {
        mockMvc.perform(get("/personwithcars")
                        .param("personid", "personid"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}