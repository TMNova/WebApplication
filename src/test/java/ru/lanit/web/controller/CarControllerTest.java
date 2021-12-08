package ru.lanit.web.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.repository.PersonRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class CarControllerTest extends AbstractTestClass {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CarController carController;

    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @MockBean
    BindingResult bindingResult;

    private Long idValidPerson;
    private Long idNonValidPerson;
    private Long idNotExistPerson;

    @BeforeEach
    public void setup() {
        PersonDTO validPerson = new PersonDTO();
        PersonDTO nonValidForCarPerson = new PersonDTO();

        validPerson.setId(20L);
        validPerson.setName("Olya");
        validPerson.setBirthday(LocalDate.of(2000,01,01));

        nonValidForCarPerson.setId(21L);
        nonValidForCarPerson.setName("Alex");
        nonValidForCarPerson.setBirthday(LocalDate.of(2020,01,01));

        idValidPerson = validPerson.getId();
        idNonValidPerson = nonValidForCarPerson.getId();
        idNotExistPerson = 99999L;

        if(!personRepository.existsById(idValidPerson)) {
            personController.addPerson(validPerson, bindingResult);
        }
        if(!personRepository.existsById(idNonValidPerson)) {
            personController.addPerson(nonValidForCarPerson, bindingResult);
        }
    }

    @Test
    void addCarShouldReturnIsOk() throws Exception {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(8L);
        carDTO.setModel("Volkswagen-Tiguan");
        carDTO.setHorsepower(150);
        carDTO.setOwnerId(idValidPerson);

        CarDTO carDTOFromController = carController.addCar(carDTO, bindingResult);

        Assert.assertEquals(carDTO, carDTOFromController);
    }

    @Test
    void addCarJSONShouldReturnIsOk() throws Exception {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(9L);
        carDTO.setModel("Volkswagen-Tiguan");
        carDTO.setHorsepower(150);
        carDTO.setOwnerId(idValidPerson);

        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\":9," +
                        "\"model\":\"Volkswagen-Tiguan\"," +
                        "\"horsepower\":150," +
                        "\"ownerId\":20" +
                        "}"));
    }

    @Test
    void addCarNonValidPersonShouldThrowBadRequest() throws Exception {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(7L);
        carDTO.setModel("Volkswagen-Tiguan");
        carDTO.setHorsepower(150);
        carDTO.setOwnerId(idNonValidPerson);

        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCarNonExistPersonShouldThrowBadRequest() throws Exception {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(7L);
        carDTO.setModel("Volkswagen-Tiguan");
        carDTO.setHorsepower(150);
        carDTO.setOwnerId(idNotExistPerson);

        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCarFieldWithNullShouldThrowBadRequest() throws Exception {
        CarDTO carOne = new CarDTO();
        CarDTO carTwo = new CarDTO();
        CarDTO carThree = new CarDTO();
        CarDTO carFour = new CarDTO();

        carOne.setModel("Volkswagen-Tiguan");
        carOne.setHorsepower(150);
        carOne.setOwnerId(idValidPerson);

        carTwo.setId(10L);
        carTwo.setHorsepower(150);
        carTwo.setOwnerId(idValidPerson);

        carThree.setId(11L);
        carThree.setModel("Volkswagen-Tiguan");
        carThree.setOwnerId(idValidPerson);

        carFour.setId(12L);
        carFour.setModel("Volkswagen-Tiguan");
        carFour.setHorsepower(150);

        ResultMatcher status = status().isBadRequest();
        checkNotNullFieldCar(carOne, status);
        checkNotNullFieldCar(carTwo, status);
        checkNotNullFieldCar(carThree, status);
        checkNotNullFieldCar(carFour, status);

    }

    @Test
    void addCarHorsepowerShouldThrowBadRequest() throws Exception {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(7L);
        carDTO.setModel("Volkswagen-Tiguan");
        carDTO.setHorsepower(-20);
        carDTO.setOwnerId(idNotExistPerson);

        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void checkNotNullFieldCar(CarDTO car, ResultMatcher status) throws Exception {
        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(car)))
                .andDo(print())
                .andExpect(status);
    }

}