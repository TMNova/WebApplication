package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.StatisticsDTO;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

@Service
public class StatisticsService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;

    @Autowired
    public StatisticsService(PersonRepository personRepository, CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }

    public StatisticsDTO getStatistics() {
        StatisticsDTO statistics = new StatisticsDTO();
        statistics.setPersonCount(personRepository.count());
        statistics.setCarCount(carRepository.count());
        statistics.setUniqueVendorCount(carRepository.countDistinctVendor());

        return statistics;
    }
}
