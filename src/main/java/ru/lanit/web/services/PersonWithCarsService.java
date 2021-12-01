package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.dto.PersonWithCarsDTO;
import ru.lanit.web.entity.Car;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.BadRequestException;
import ru.lanit.web.exceptions.NotFoundException;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonWithCarsService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;

    @Autowired
    public PersonWithCarsService(PersonRepository personRepository, CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }

    public PersonWithCarsDTO getPersonWithCars(String id) {
        try {
            Long personId = Long.parseLong(id);
            checkPersonToExistById(personId);
            Person person = personRepository.getById(personId);
            List<Car> carsList = carRepository.findAllByOwnerId_Id(personId);
            List<CarDTO> carDTOList = new ArrayList<>();

            for(Car car : carsList) {
                CarDTO carDTO = new CarDTO();
                carDTO.setId(car.getId());
                carDTO.setModel(car.getVendor() + "-" + car.getModel());
                carDTO.setHorsepower(car.getHorsepower());
                carDTO.setOwnerId(car.getOwnerId().getId());
                carDTOList.add(carDTO);
            }

            PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO();
            personWithCarsDTO.setId(person.getId());
            personWithCarsDTO.setName(person.getName());
            personWithCarsDTO.setBirthday(person.getBirthday());
            personWithCarsDTO.setCars(carDTOList);

            return personWithCarsDTO;
        } catch (NumberFormatException e) {
            throw new BadRequestException("Number format exception");
        }
    }

    private void checkPersonToExistById(Long personId) {
        if(!personRepository.existsById(personId))
            throw new NotFoundException("Person with ID:" + personId + "  is not exist in database!");
    }

}
