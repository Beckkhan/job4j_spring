package ru.job4j.carwarehouse.storage;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carwarehouse.models.Car;
import java.util.Date;
import java.util.List;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 13.08.2019
 */

@Repository
public interface CarStorage extends CrudRepository<Car, Integer> {

    @Query("select distinct C.location from Car C")
    List<String> getLocations();

    @Modifying
    @Query(value = "select * from cars C where C.created >= ?", nativeQuery = true)
    List<Car> findCarsByCreated(Date date);

    @Modifying
    @Query(value = "select * from cars C where lower(C.brand) = ? and C.created >= ?", nativeQuery = true)
    List<Car> findCarsByBrand(String brand, Date date);

    @Modifying
    @Query(value = "select * from cars C where C.sold = ? and C.created >= ?", nativeQuery = true)
    List<Car> findCarsBySold(boolean sold, Date date);

    @Modifying
    @Query(value = "select * from cars C where C.sold = ? and lower(C.brand) = ? and C.created >= ?", nativeQuery = true)
    List<Car> findCarsBySoldAndBrand(boolean sold, String brand, Date date);
}