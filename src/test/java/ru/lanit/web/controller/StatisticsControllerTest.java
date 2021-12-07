package ru.lanit.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.lanit.web.AbstractTestClass;
import ru.lanit.web.WebApplication;
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;
import ru.lanit.web.services.CarService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-for-statistics.sql"})
public class StatisticsControllerTest extends AbstractTestClass {

    @Autowired
    StatisticsController statisticsController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setup() {

    }

    @Test
    public void getStatisticsShouldReturnIsOk() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"personcount\":3," +
                        "\"carcount\":4," +
                        "\"uniquevendorcount\":3" +
                        "}"));
    }

    @Test
    public void getStatisticsWithSameVendorShouldReturnIsOk() throws Exception {
        CarDTO car = new CarDTO();
        CarDTO sameCar = new CarDTO();

        car.setId(5L);
        car.setModel("La-Da-Devyatka");
        car.setHorsepower(10);
        car.setOwnerId(1L);

        sameCar.setId(6L);
        sameCar.setModel("Lada-Devyatka");
        sameCar.setHorsepower(10);
        sameCar.setOwnerId(1L);

        carService.createCar(car);
        carService.createCar(sameCar);

        mockMvc.perform(get("/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"personcount\":3," +
                        "\"carcount\":6," +
                        "\"uniquevendorcount\":4" +
                        "}"));
    }
}