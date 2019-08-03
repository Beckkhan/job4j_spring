package ru.job4j.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Function;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 03.08.2019
 */

@Component
public class JdbcStorage implements Storage {

    private SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private <T> T trx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public int addUser(User user) {
        return this.trx(session -> {
            session.save(user);
            return user.getId();
        });
    }

    @Override
    public User getUser(int id) {
        return this.trx(session -> session.get(User.class, id));
    }

    @Override
    public List<User> getAll() {
        return this.trx(session ->
                session.createQuery("from User", User.class).list());
    }
}