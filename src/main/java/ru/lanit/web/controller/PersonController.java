package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lanit.web.dto.PersonDTO;
import ru.lanit.web.exceptions.BadRequestException;
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
            throw new BadRequestException("Bad Request");
        }

        personService.createPerson(personDTO);

        return personDTO;
    }
}
