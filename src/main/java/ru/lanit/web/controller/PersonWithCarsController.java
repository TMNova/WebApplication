package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.lanit.web.dto.PersonWithCarsDTO;
import ru.lanit.web.services.PersonWithCarsService;

@RestController
@RequestMapping("/personwithcars")
public class PersonWithCarsController {

    @Autowired
    private PersonWithCarsService personWithCarsService;

    @GetMapping
    public PersonWithCarsDTO getPersonWithCars(@RequestParam(name = "personid") String id) {

        return personWithCarsService.getPersonWithCars(id);
    }
}
