package ru.lanit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lanit.web.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findAllByOwnerId_Id(Long id);

    @Query(value = "SELECT COUNT(DISTINCT vendor) FROM Car")
    public Long countDistinctVendor();
}
