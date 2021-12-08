package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.exceptions.person.PersonDTOValidationException;
import ru.lanit.web.services.PersonService;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public PersonDTO addPerson(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new PersonDTOValidationException();
        }

        personService.createPerson(personDTO);

        return personDTO;
    }
}
