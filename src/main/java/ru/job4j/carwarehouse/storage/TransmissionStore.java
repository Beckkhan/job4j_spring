package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.Transmission;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class TransmissionStore implements Store<Transmission> {

    private static TransmissionStore instance = new TransmissionStore();

    public static TransmissionStore getInstance() {
        return instance;
    }

    private TransmissionStore() {
    }

    @Override
    public int add(Transmission transmission) {
        return tx(session -> {
            session.saveOrUpdate(transmission); return transmission.getId();
        });
    }

    @Override
    public int update(Transmission transmission) {
        return tx(session -> {
            session.update(transmission); return transmission.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            Transmission transmission = session.get(Transmission.class, id);
            session.delete(transmission);
            return transmission.getId();
        });
    }

    @Override
    public List<Transmission> getAll() {
        return tx(session -> session.createQuery("from Transmission ", Transmission.class).list());
    }

    @Override
    public Transmission getById(int id) {
        return tx(session -> session.get(Transmission.class, id));
    }

    @Override
    public Transmission getByName(String transmissionName) {
        Transmission transmission;
        try {
            transmission = tx(session ->
                    session.createQuery("select T from Transmission T where T.name = : name", Transmission.class)
                            .setParameter("name", transmissionName).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return transmission;
    }

    public List<String> getTransmissionTypes() {
        return tx(session -> session.createQuery("select distinct T.name from Transmission T").list());
    }
}