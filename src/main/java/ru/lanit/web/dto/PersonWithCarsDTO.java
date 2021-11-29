package ru.lanit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.lanit.web.entity.Car;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PersonWithCarsDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @NotNull
    private List<Car> cars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
