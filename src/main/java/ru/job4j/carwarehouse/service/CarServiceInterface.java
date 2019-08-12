package ru.job4j.carwarehouse.service;

import ru.job4j.carwarehouse.models.*;
import java.util.Date;
import java.util.List;

public interface CarServiceInterface {

    int addBody(Bodytype bodytype);

    void deleteBody(Bodytype body);

    List<String> getBodyTypes();

    int addEngine(Engine engine);

    void deleteEngine(Engine engine);

    List<String> getEngineTypes();

    int addTransmission(Transmission transmission);

    void deleteTransmission(Transmission transmission);

    List<String> getTransmissionTypes();

    int addPicture(Picture picture);

    List<Picture> getPicturesByCar(Car car);

    int addCar(Car car);

    void deleteCar(Car car);

    List<Car> getAllCar();

    List<Car> getAllCarsWithData(Date date);

    Car getCarById(int id);

    Car constructCar(Car car, Bodytype bodyType, Engine engine, Transmission transmission);

    List<String> getLocation();

    void statusChange(int id, boolean done);

    List<Car> filterCarsBySold(boolean sold, Date date);

    List<Car> filterCarsBySoldAndBrand(boolean sold, String brand, Date date);

    List<Car> filterCarsByBrand(String brand, Date date);
}