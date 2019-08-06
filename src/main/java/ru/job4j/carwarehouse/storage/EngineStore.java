package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.Engine;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class EngineStore implements Store<Engine> {

    private static EngineStore instance = new EngineStore();

    public static EngineStore getInstance() {
        return instance;
    }

    private EngineStore() {
    }

    @Override
    public int add(Engine engine) {
        return tx(session -> {
            session.saveOrUpdate(engine); return engine.getId();
        });
    }

    @Override
    public int update(Engine engine) {
        return tx(session -> {
            session.update(engine); return engine.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            Engine engine = session.get(Engine.class, id);
            session.delete(engine);
            return engine.getId();
        });
    }

    @Override
    public List<Engine> getAll() {
        return tx(session -> session.createQuery("from Engine ", Engine.class).list());
    }

    @Override
    public Engine getById(int id) {
        return tx(session -> session.get(Engine.class, id));
    }

    @Override
    public Engine getByName(String engineName) {
        Engine engine;
        try {
            engine = tx(session ->
                    session.createQuery("select E from Engine E where E.name = : name", Engine.class)
                            .setParameter("name", engineName).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return engine;
    }

    public List<String> getEngineTypes() {
        return tx(session -> session.createQuery("select distinct E.name from Engine E").list());
    }
}