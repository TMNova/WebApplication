package ru.lanit.web.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.web.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminConsoleServiceTest {

    @Autowired
    AdminConsoleService adminConsoleService;

    @MockBean
    PersonRepository personRepository;

    @Test
    public void clearAllTables() {
        Assertions.assertDoesNotThrow(() -> personRepository.deleteAll());
        Mockito.verify(personRepository, Mockito.times(1)).deleteAll();
    }

}