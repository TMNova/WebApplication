package ru.lanit.web.dto;

import java.util.Objects;

public class StatisticsDTO {

    private Long personcount;
    private Long carcount;
    private Long uniquevendorcount;

    public Long getPersoncount() {
        return personcount;
    }

    public void setPersoncount(Long personcount) {
        this.personcount = personcount;
    }

    public Long getCarcount() {
        return carcount;
    }

    public void setCarcount(Long carcount) {
        this.carcount = carcount;
    }

    public Long getUniquevendorcount() {
        return uniquevendorcount;
    }

    public void setUniquevendorcount(Long uniquevendorcount) {
        this.uniquevendorcount = uniquevendorcount;
    }

}
