package ru.lanit.web.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.dto.PersonWithCarsDTO;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.BadRequestException;
import ru.lanit.web.exceptions.NotFoundException;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonWithCarsServiceTest {

    @Autowired
    PersonWithCarsService personWithCarsService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    CarRepository carRepository;

    @Test
    public void getPersonWithCars() {
        when(personRepository.existsById(anyLong())).thenReturn(true);
        when(personRepository.getById(anyLong())).thenReturn(new Person());

        String id = "1";
        PersonWithCarsDTO personExpect = new PersonWithCarsDTO();
        personExpect.setCars(new ArrayList<>());
        PersonWithCarsDTO personActual = personWithCarsService.getPersonWithCars(id);

        Assertions.assertEquals(personExpect.getId(), personActual.getId());
        Assertions.assertEquals(personExpect.getName(), personActual.getName());
        Assertions.assertEquals(personExpect.getBirthdate(), personActual.getBirthdate());
        Assertions.assertEquals(personExpect.getCars(), personActual.getCars());
        Mockito.verify(personRepository, Mockito.times(1)).existsById(anyLong());
        Mockito.verify(personRepository, Mockito.times(1)).getById(anyLong());
    }

    @Test
    public void getPersonWithCarsParsingError() {
        Assertions.assertThrows(BadRequestException.class, () -> personWithCarsService.getPersonWithCars("one"));
    }

    @Test
    public void getPersonWithCarsNotExistPersonFail() {
        when(personRepository.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> personWithCarsService.getPersonWithCars("1"));
        Mockito.verify(personRepository, Mockito.times(1)).existsById(anyLong());
        Mockito.verify(personRepository, Mockito.times(0)).getById(anyLong());
    }
}