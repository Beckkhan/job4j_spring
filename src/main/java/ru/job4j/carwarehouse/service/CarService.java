package ru.job4j.carwarehouse.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carwarehouse.models.*;
import ru.job4j.carwarehouse.storage.*;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @since 13.08.2019
 */

@Component
public class CarService implements CarServiceInterface {

    @Autowired
    private BodyStorage bodyStorage;

    @Autowired
    private EngineStorage engineStorage;

    @Autowired
    private TransmStorage transmStorage;

    @Autowired
    private PictureStorage pictureStorage;

    @Autowired
    private CarStorage carStorage;

    @Override
    public int addBody(Bodytype bodytype) {
        return bodyStorage.save(bodytype).getId();
    }

    @Override
    public void deleteBody(Bodytype bodytype) {
        bodyStorage.delete(bodytype);
    }

    @Override
    public List<String> getBodyTypes() {
        return bodyStorage.getBodytypes();
    }

    @Override
    public int addEngine(Engine engine) {
        return engineStorage.save(engine).getId();
    }

    @Override
    public void deleteEngine(Engine engine) {
        engineStorage.delete(engine);
    }

    @Override
    public List<String> getEngineTypes() {
        return engineStorage.getEngines();
    }

    @Override
    public int addTransmission(Transmission transmission) {
        return transmStorage.save(transmission).getId();
    }

    @Override
    public void deleteTransmission(Transmission transmission) {
        transmStorage.delete(transmission);
    }

    @Override
    public List<String> getTransmissionTypes() {
        return transmStorage.getTransmissions();
    }

    @Override
    public int addPicture(Picture picture) {
        return pictureStorage.save(picture).getId();
    }

    @Override
    public List<Picture> getPicturesByCar(Car car) {
        return pictureStorage.findPicturesByCar(car);
    }

    @Override
    public int addCar(Car car) {
        return carStorage.save(car).getId();
    }

    @Override
    public void deleteCar(Car car) {
        carStorage.delete(car);
    }

    @Override
    public List<Car> getAllCar() {
        return Lists.newArrayList(carStorage.findAll());
    }

    @Override
    public List<Car> getAllCarsWithData(Date date) {
        return Lists.newArrayList(carStorage.findCarsByCreated(date));
    }

    @Override
    public Car getCarById(int id) {
        return carStorage.findById(id).orElse(null);
    }

    @Override
    public Car constructCar(Car car, Bodytype bodyType, Engine engine, Transmission transmission) {
        Bodytype carBody = bodyStorage.findByName(bodyType.getName());
        if (carBody == null) {
            carBody = new Bodytype(bodyType.getName());
            bodyStorage.save(carBody);
        }
        car.setBodytype(carBody);

        Engine carEngine = engineStorage.findByName(engine.getName());
        if (carEngine == null) {
            carEngine = new Engine(engine.getName());
            engineStorage.save(carEngine);
        }
        car.setEngine(carEngine);

        Transmission carTrans = transmStorage.findByName(transmission.getName());
        if (carTrans == null) {
            carTrans = new Transmission(transmission.getName());
            transmStorage.save(carTrans);
        }
        car.setTransmission(carTrans);

        return car;
    }

    @Override
    public List<String> getLocation() {
        return carStorage.getLocations();
    }

    @Override
    public void statusChange(int id, boolean done) {
        Car car = this.getCarById(id);
        car.setSold(done);
        carStorage.save(car);
    }

    @Override
    public List<Car> filterCarsBySold(boolean sold, Date date) {
        return carStorage.findCarsBySold(sold, date);
    }

    @Override
    public List<Car> filterCarsBySoldAndBrand(boolean sold, String brand, Date date) {
        return carStorage.findCarsBySoldAndBrand(sold, brand, date);
    }

    @Override
    public List<Car> filterCarsByBrand(String brand, Date date) {
        return carStorage.findCarsByBrand(brand.toLowerCase(), date);
    }
}