package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.CarDTO;
import ru.lanit.web.entity.Car;
import ru.lanit.web.exceptions.ObjectExistException;
import ru.lanit.web.exceptions.ValidationException;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Autowired
    public CarService(CarRepository carRepository, PersonRepository personRepository) {
        this.carRepository = carRepository;
        this.personRepository = personRepository;
    }

    public void createCar(CarDTO carDTO) throws Exception {
        checkPersonToNotExistById(carDTO.getOwnerId());

        Map<String, String> vendorModelFormat = toVendorModelFormat(carDTO.getModel());

        checkPersonToMinAgeForDriveCar(carDTO.getOwnerId());
        checkCarToExistById(carDTO.getId());

        Car car = new Car();
        car.setId(carDTO.getId());
        car.setVendor(vendorModelFormat.get("Vendor"));
        car.setModel(vendorModelFormat.get("Model"));
        car.setHorsepower(carDTO.getHorsepower());
        car.setPerson(personRepository.getById(carDTO.getOwnerId()));

        carRepository.save(car);
    }

    private void checkPersonToMinAgeForDriveCar(Long ownerId) throws ValidationException {
        LocalDate birthday = personRepository.getById(ownerId).getBirthday();
        LocalDate now = LocalDate.now();
        int yearsDifference = Period.between(birthday, now).getYears();
        if(yearsDifference < 18) throw new ValidationException();
    }

    private void checkPersonToNotExistById(Long personId) throws ObjectExistException {
        if(!personRepository.existsById(personId))
            throw new ObjectExistException();
    }

    private void checkCarToExistById(Long carId) throws ObjectExistException {
        if(carRepository.existsById(carId))
            throw new ObjectExistException();
    }

    private Map<String, String> toVendorModelFormat(String model) throws ValidationException {
        if(model.charAt(0) == '-') throw new ValidationException();
        String[] strings = model.split("-");
        StringBuilder vendor = new StringBuilder();
        String modelNew = "";
        if (model.charAt(model.length() - 1) == '-') {
            modelNew = "-";
        }

        for(int i = 0; i < strings.length - 1; i++) {
            vendor.append(strings[i]);
            if(i < strings.length - 2) vendor.append("-");
        }

        String finalModelNew = modelNew;
        return new HashMap<String, String>() {{
            put("Vendor", vendor.toString());
            put("Model", strings[strings.length - 1] + finalModelNew);
        }};
    }
}
