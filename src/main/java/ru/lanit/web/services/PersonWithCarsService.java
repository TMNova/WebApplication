package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.PersonWithCarsDTO;
import ru.lanit.web.entity.Car;
import ru.lanit.web.entity.Person;
import ru.lanit.web.exceptions.BadRequestException;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

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

    public PersonWithCarsDTO getPersonWithCars(Long personId) {
        checkPersonToExistById(personId);
        Person person = personRepository.getById(personId);
        List<Car> cars = carRepository.findAllByOwnerId_Id(personId);




        PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO();
        personWithCarsDTO.setId(person.getId());
        personWithCarsDTO.setName(person.getName());
        personWithCarsDTO.setBirthday(person.getBirthday());
        personWithCarsDTO.setCars(cars);

        return personWithCarsDTO;
    }

    private void checkPersonToExistById(Long personId) {
        if(!personRepository.existsById(personId))
            throw new BadRequestException("Person with ID:" + personId + "  is not exist in database!");
    }

}
