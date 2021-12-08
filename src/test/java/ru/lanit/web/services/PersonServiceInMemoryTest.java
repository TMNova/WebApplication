package ru.lanit.web.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.WebApplication;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.ObjectExistException;
import ru.lanit.web.repository.PersonRepository;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource("/application-test.properties")
public class PersonServiceInMemoryTest {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;


    @Before
    public void setup() {
        Person validPersonOne = new Person();
        Person validPersonTwo = new Person();

        validPersonOne.setId(1L);
        validPersonOne.setName("Olya");
        validPersonOne.setBirthday(LocalDate.of(2000,01,01));

        validPersonTwo.setId(2L);
        validPersonTwo.setName("Alex");
        validPersonTwo.setBirthday(LocalDate.of(2020,01,01));

        personRepository.save(validPersonOne);
        personRepository.save(validPersonTwo);
    }

    @Test
    public void createPerson() throws Exception {
        PersonDTO personExpect = new PersonDTO();
        personExpect.setId(3L);
        personExpect.setName("Olga");
        personExpect.setBirthday(LocalDate.of(2000, 10, 10));

        personService.createPerson(personExpect);

        Person personActual = personRepository.getById(3L);

        Assert.assertEquals(personExpect.getId(), personActual.getId());
        Assert.assertEquals(personExpect.getName(), personActual.getName());
        Assert.assertEquals(personExpect.getBirthdate(), personActual.getBirthday());
    }

    @Test
    public void createPersonIsExistFail() {
        PersonDTO personExpect = new PersonDTO();
        personExpect.setId(2L);
        personExpect.setName("Olga");
        personExpect.setBirthday(LocalDate.of(2000, 10, 10));

        Assert.assertThrows(ObjectExistException.class, () -> personService.createPerson(personExpect));
    }

}
