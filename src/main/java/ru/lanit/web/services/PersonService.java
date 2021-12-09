package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.BusinessServiceException;
import ru.lanit.web.exceptions.ObjectAlreadyExistException;
import ru.lanit.web.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void createPerson(PersonDTO personDTO) throws BusinessServiceException {
        checkPersonToExistById(personDTO.getId());

        Person person = new Person();

        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setBirthday(personDTO.getBirthdate());
        personRepository.save(person);
    }

    private void checkPersonToExistById(Long personId) throws ObjectAlreadyExistException {
        if(personRepository.existsById(personId))
            throw new ObjectAlreadyExistException();
    }

}
