package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.PersonAlreadyExistInDBException;
import ru.lanit.web.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void createPerson(PersonDTO personDTO) {
        checkPersonToExistById(personDTO.getId());

        Person person = new Person();

        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setBirthday(personDTO.getBirthdate());
        personRepository.save(person);
    }

    private void checkPersonToExistById(Long personId) {
        if(personRepository.existsById(personId))
            throw new PersonAlreadyExistInDBException();
    }

}
