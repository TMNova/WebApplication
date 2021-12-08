package ru.lanit.web.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.exceptions.PersonAlreadyExistInDBException;
import ru.lanit.web.repository.PersonRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class PersonServiceTest {

    @Autowired
    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    @Test
    void createPerson() {
        PersonDTO personDTO = new PersonDTO();

        when(personRepository.save(any())).thenReturn(any());

        Assertions.assertDoesNotThrow(() -> personService.createPerson(personDTO));
        Mockito.verify(personRepository, Mockito.times(1)).save(any());
    }

    @Test
    void createPersonFail() {
        PersonDTO personDTO = new PersonDTO();

        when(personRepository.existsById(personDTO.getId())).thenThrow(PersonAlreadyExistInDBException.class);

        Assertions.assertThrows(PersonAlreadyExistInDBException.class, () -> personService.createPerson(personDTO));
        Mockito.verify(personRepository, Mockito.times(0)).save(any());
    }
}