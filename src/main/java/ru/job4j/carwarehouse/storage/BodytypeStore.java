package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.Bodytype;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class BodytypeStore implements Store<Bodytype> {

    private static BodytypeStore instance = new BodytypeStore();

    public static BodytypeStore getInstance() {
        return instance;
    }

    private BodytypeStore() {
    }

    @Override
    public int add(Bodytype bodytype) {
        return tx(session -> {
            session.saveOrUpdate(bodytype); return bodytype.getId();
        });
    }

    @Override
    public int update(Bodytype bodytype) {
        return tx(session -> {
            session.update(bodytype); return bodytype.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            Bodytype bodytype = session.get(Bodytype.class, id);
            session.delete(bodytype);
            return bodytype.getId();
        });
    }

    @Override
    public List<Bodytype> getAll() {
        return tx(session -> session.createQuery("from Bodytype ", Bodytype.class).list());
    }

    @Override
    public Bodytype getById(int id) {
        return tx(session -> session.get(Bodytype.class, id));
    }

    @Override
    public Bodytype getByName(String bodyName) {
        Bodytype bodytype;
        try {
            bodytype = tx(session ->
                    session.createQuery("select B from Bodytype B where B.name = : name", Bodytype.class).setParameter("name", bodyName).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return bodytype;
    }

    public List<String> getBodyTypes() {
        return tx(session -> session.createQuery("select distinct B.name from Bodytype B").list());
    }
}