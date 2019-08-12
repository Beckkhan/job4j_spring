package ru.job4j.carwarehouse.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carwarehouse.models.Transmission;
import java.util.List;

/**
 * @version 1.0
 * @since 13.08.2019
 */

@Repository
public interface TransmStorage extends CrudRepository<Transmission, Integer> {

    Transmission findByName(String name);

    @Query("select distinct T.name from Transmission T")
    List<String> getTransmissions();
}