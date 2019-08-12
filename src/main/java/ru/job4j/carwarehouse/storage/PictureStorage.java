package ru.job4j.carwarehouse.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carwarehouse.models.Car;
import ru.job4j.carwarehouse.models.Picture;
import java.util.List;

/**
 * @version 1.0
 * @since 13.08.2019
 */

@Repository
public interface PictureStorage extends CrudRepository<Picture, Integer> {

    List<Picture> findPicturesByCar(Car car);
}