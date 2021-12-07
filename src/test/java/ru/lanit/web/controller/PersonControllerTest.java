package ru.lanit.web.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;
import ru.lanit.web.AbstractTestClass;
import ru.lanit.web.WebApplication;
import ru.lanit.web.dto.PersonDTO;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class PersonControllerTest extends AbstractTestClass {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonController personController;

    @MockBean
    BindingResult bindingResult;

    @Test
    void addPersonShouldReturnIsOk() {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(1L);
        personDTO.setName("Olya");
        personDTO.setBirthday(LocalDate.of(2000, 12, 12));

        PersonDTO personDTOFromController = personController.addPerson(personDTO, bindingResult);

        Assert.assertEquals(personDTO, personDTOFromController);
    }

    @Test
    void addPersonJSONReturnIsOk() throws Exception {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(2L);
        personDTO.setName("Olya");
        personDTO.setBirthday(LocalDate.of(2000, 12, 12));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":\"Olya\",\n" +
                        "    \"birthdate\":\"12.12.2000\"\n" +
                        "}"))
                .andReturn();
    }

    @Test
    void addPersonWithNullFieldShouldThrowBadRequest() throws Exception {

        PersonDTO personOne = new PersonDTO();
        PersonDTO personTwo = new PersonDTO();
        PersonDTO personThree = new PersonDTO();

        personOne.setName("Olya");
        personOne.setBirthday(LocalDate.of(2000, 12, 12));

        personTwo.setId(2L);
        personTwo.setBirthday(LocalDate.of(2000, 12, 12));

        personThree.setId(3L);
        personThree.setName("Olya");

        ResultMatcher status = status().isBadRequest();
        checkPersonWithNullField(personOne, status);
        checkPersonWithNullField(personTwo, status);
        checkPersonWithNullField(personThree, status);
    }

    @Test
    void addPersonWithFutureBirthdateShouldThrowBadRequest() throws Exception {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(1L);
        personDTO.setName("Olya");
        personDTO.setBirthday(LocalDate.of(2050, 12, 12));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addPersonWithNonValidDateFormatShouldThrowBadRequest() throws Exception {
        String firstDateFormat = "2020.10.10";
        String secondDateFormat = "10.31.2020";
        String thirdDateFormat = "100.300.200000";
        String fourDateFormat = "99.99.9999";

        ResultMatcher status = status().isBadRequest();
        checkDateFormat(firstDateFormat, status);
        checkDateFormat(secondDateFormat, status);
        checkDateFormat(thirdDateFormat, status);
        checkDateFormat(fourDateFormat, status);
    }

    private void checkDateFormat(String date, ResultMatcher status) throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\":1,\n" +
                                "    \"name\":\"Olya\",\n" +
                                "    \"birthdate\":\""+ date + "\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status);
    }

    private void checkPersonWithNullField(PersonDTO person, ResultMatcher status) throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person)))
                .andDo(print())
                .andExpect(status);
    }
}