package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class CarStore implements Store<Car> {

    private static final CarStore INSTANCE = new CarStore();

    private final BodytypeStore bodytypeStore = BodytypeStore.getInstance();

    private final EngineStore engineStore = EngineStore.getInstance();

    private final TransmissionStore transmissionStore = TransmissionStore.getInstance();

    public static CarStore getInstance() {
        return INSTANCE;
    }

    private CarStore() {
    }

    @Override
    public int add(Car car) {
        return tx(session ->  {
            session.saveOrUpdate(car);
            return car.getId();
        });
    }

    @Override
    public int update(Car car) {
        return tx(session -> {
            session.update(car); return car.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            Car car = session.get(Car.class, id);
            session.delete(car);
            return car.getId();
        });
    }

    @Override
    public List<Car> getAll() {
        return tx(session -> session.createQuery("from Car", Car.class).list());
    }

    public List<Car> getAllWithData(Date date) {
        return tx(session -> session.createQuery("from Car C where C.created >= : date", Car.class)
                .setParameter("date", date).list());
    }

    @Override
    public Car getById(int id) {
        return tx(session -> session.get(Car.class, id));
    }

    @Override
    public Car getByName(String carBrand) {
        Car car;
        try {
            car = tx(session ->
                    session.createQuery("select C from Car C where C.brand = : brand", Car.class)
                            .setParameter("brand", carBrand).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return car;
    }

    public Car constructCar(Car car, Bodytype bodyType, Engine engine, Transmission transmission) {
        Bodytype carBody = bodytypeStore.getByName(bodyType.getName());
        if (carBody == null) {
            carBody = new Bodytype(bodyType.getName());
            bodytypeStore.add(carBody);
        }
        car.setBodytype(carBody);

        Engine carEngine = engineStore.getByName(engine.getName());
        if (carEngine == null) {
            carEngine = new Engine(engine.getName());
            engineStore.add(carEngine);
        }
        car.setEngine(carEngine);

        Transmission carTrans = transmissionStore.getByName(transmission.getName());
        if (carTrans == null) {
            carTrans = new Transmission(transmission.getName());
            transmissionStore.add(carTrans);
        }
        car.setTransmission(carTrans);

        return car;
    }

    public List<String> getLocation() {
        return tx(session -> session.createQuery("select distinct C.location from Car C").list());
    }

    public void statusChange(int id, boolean done) {
        tx(session -> {
            Car car = session.get(Car.class, id);
            car.setSold(done);
            return null;
        });
    }

    public List<Car> filterCarsBySold(boolean sold, Date date) {
        return tx(session -> session.createQuery("select C from Car C where C.sold = : sold and C.created >= : date", Car.class)
                .setParameter("sold", sold).setParameter("date", date).getResultList());
    }

    public List<Car> filterCarsBySoldAndName(boolean sold, String name, Date date) {
        final String parameter = name.toLowerCase();
        return tx(session ->
                session.createQuery("select C from Car C where C.sold = : sold and lower(C.brand) = : name and C.created >= : date", Car.class)
                        .setParameter("sold", sold)
                        .setParameter("name", parameter)
                        .setParameter("date", date)
                        .getResultList());
    }

    public List<Car> filterCarsByName(String name, Date date) {
        //final String parameter = name.toLowerCase();
        List<Car> allList = this.getAllWithData(date);
        /*List<Car> allList = tx(session -> session.createQuery("select C from Car C where lower(C.brand) = : name", Car.class)
                .setParameter("name", parameter).getResultList());*/
        List<Car> result = new ArrayList<>();
        for (Car car: allList) {
            if (car.getBrand().compareToIgnoreCase(name) == 0
                    || car.getBrand().toLowerCase().contains(name.toLowerCase())) {
                result.add(car);
            }
        }
        return result;
    }
}