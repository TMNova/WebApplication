package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.repository.PersonRepository;

@Service
public class AdminConsoleService {

    private final PersonRepository personRepository;

    @Autowired
    public AdminConsoleService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void clearAllTables() {
        personRepository.deleteAll();
    }
}
