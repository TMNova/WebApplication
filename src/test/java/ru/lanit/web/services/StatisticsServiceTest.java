package ru.lanit.web.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.dto.StatisticsDTO;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import javax.swing.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

    @Autowired
    StatisticsService statisticsService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    CarRepository carRepository;

    @Test
    public void getStatistics() {
        StatisticsDTO statisticsExpect = new StatisticsDTO();
        statisticsExpect.setCarcount(0L);
        statisticsExpect.setPersoncount(0L);
        statisticsExpect.setUniquevendorcount(0L);
        StatisticsDTO statisticsActual = statisticsService.getStatistics();

        Assertions.assertEquals(statisticsExpect.getCarcount(), statisticsActual.getCarcount());
        Assertions.assertEquals(statisticsExpect.getPersoncount(), statisticsActual.getPersoncount());
        Assertions.assertEquals(statisticsExpect.getUniquevendorcount(), statisticsActual.getUniquevendorcount());
    }
}