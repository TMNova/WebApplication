package ru.lanit.web.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.CarAlreadyExistInDBException;
import ru.lanit.web.exceptions.PersonMinAgeForCarException;
import ru.lanit.web.exceptions.PersonNotExistInDBException;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    CarService carService;

    @MockBean
    CarRepository carRepository;

    @MockBean
    PersonRepository personRepository;

    @Test
    public void createCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setModel("Volkswagen-Tiguan");

        Person person = new Person();
        person.setBirthday(LocalDate.of(2000,12,12));

        when(personRepository.existsById(carDTO.getOwnerId())).thenReturn(true);
        when(personRepository.getById(carDTO.getOwnerId())).thenReturn(person);

        Assertions.assertDoesNotThrow(() -> carService.createCar(carDTO));
        Mockito.verify(carRepository, Mockito.times(1)).save(any());
        Mockito.verify(carRepository, Mockito.times(1)).existsById(carDTO.getId());
        Mockito.verify(personRepository, Mockito.times(1)).existsById(carDTO.getOwnerId());
    }

    @Test
    public void createCarPersonNotExistFail() {
        CarDTO carDTO = new CarDTO();
        carDTO.setModel("Volkswagen-Tiguan");

        when(personRepository.existsById(carDTO.getOwnerId())).thenReturn(false);

        Assertions.assertThrows(PersonNotExistInDBException.class, () -> carService.createCar(carDTO));
        Mockito.verify(carRepository, Mockito.times(0)).save(any());
        Mockito.verify(carRepository, Mockito.times(0)).existsById(carDTO.getId());
        Mockito.verify(personRepository, Mockito.times(1)).existsById(carDTO.getOwnerId());
    }

    @Test
    public void createCarPersonAgeFail() {
        CarDTO carDTO = new CarDTO();
        carDTO.setModel("Volkswagen-Tiguan");

        Person person = new Person();
        person.setBirthday(LocalDate.of(2020,12,12));

        when(personRepository.existsById(carDTO.getOwnerId())).thenReturn(true);
        when(personRepository.getById(carDTO.getOwnerId())).thenReturn(person);

        Assertions.assertThrows(PersonMinAgeForCarException.class, () -> carService.createCar(carDTO));
        Mockito.verify(carRepository, Mockito.times(0)).save(any());
        Mockito.verify(carRepository, Mockito.times(0)).existsById(carDTO.getId());
        Mockito.verify(personRepository, Mockito.times(1)).existsById(carDTO.getOwnerId());
    }

    @Test
    public void createCarAlreadyExistFail() {
        CarDTO carDTO = new CarDTO();
        carDTO.setModel("Volkswagen-Tiguan");

        Person person = new Person();
        person.setBirthday(LocalDate.of(2000,12,12));

        when(personRepository.existsById(carDTO.getOwnerId())).thenReturn(true);
        when(personRepository.getById(carDTO.getOwnerId())).thenReturn(person);
        when(carRepository.existsById(carDTO.getId())).thenReturn(true);

        Assertions.assertThrows(CarAlreadyExistInDBException.class, () -> carService.createCar(carDTO));
        Mockito.verify(carRepository, Mockito.times(0)).save(any());
        Mockito.verify(carRepository, Mockito.times(1)).existsById(carDTO.getId());
        Mockito.verify(personRepository, Mockito.times(1)).existsById(carDTO.getOwnerId());
    }
}