package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.User;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class UserStore implements Store<User> {

    private static UserStore ourInstance = new UserStore();

    public static UserStore getInstance() {
        return ourInstance;
    }

    private UserStore() {
    }

    @Override
    public int add(User entity) {
        return tx(session -> {
            session.save(entity); return entity.getId();
        });
    }

    @Override
    public int update(User entity) {
        return tx(session -> {
            session.update(entity); return entity.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            User user = session.get(User.class, id);
            session.delete(user);
            return user.getId();
        });
    }

    @Override
    public List<User> getAll() {
        return tx(session -> session.createQuery("from User", User.class).list());
    }

    @Override
    public User getById(int id) {
        return tx(session -> session.get(User.class, id));
    }

    @Override
    public User getByName(String login) {
        User user;
        try {
            user = tx(session ->
                    session.createQuery("select U from User U where U.login = : login", User.class)
                            .setParameter("login", login).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public User loginPermit(String login, String password) {
        User user;
        try {
            user = tx(session ->
                    session.createQuery("select U from User U where U.login = ?1 and U.password = ?2", User.class)
                            .setParameter(1, login).setParameter(2, password).getSingleResult());
        } catch (Exception e) {
            return null;
        }
        return user;
    }
}