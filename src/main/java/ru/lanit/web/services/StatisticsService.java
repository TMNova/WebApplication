package ru.lanit.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.web.dto.StatisticsDTO;
import ru.lanit.web.repository.CarRepository;
import ru.lanit.web.repository.PersonRepository;

import java.util.Locale;

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
        statistics.setPersoncount(personRepository.count());
        statistics.setCarcount(carRepository.count());
        statistics.setUniquevendorcount(getUniqueVendors());

        return statistics;
    }

    private Long getUniqueVendors() {
        return carRepository.findVendors()
                .stream()
                .map(string -> String.join("", string.split("-")))
                .map(string -> string.toLowerCase(Locale.ROOT))
                .distinct()
                .count();
    }
}
