package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.web.dto.PersonWithCarsDTO;
import ru.lanit.web.services.PersonWithCarsService;

@RestController
@RequestMapping("/personwithcars")
public class PersonWithCarsController {

    @Autowired
    private PersonWithCarsService personWithCarsService;

    @GetMapping
    public PersonWithCarsDTO getPersonWithCars(@RequestParam(name = "personid") String id) throws Exception {

        return personWithCarsService.getPersonWithCars(id);
    }
}
