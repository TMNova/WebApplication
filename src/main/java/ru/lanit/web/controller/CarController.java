package ru.lanit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.exceptions.BadRequestException;
import ru.lanit.web.services.CarService;

import javax.validation.Valid;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping()
    public CarDTO addCar(@Valid @RequestBody CarDTO carDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }

        carService.createCar(carDTO);
        return carDTO;
    }


}
