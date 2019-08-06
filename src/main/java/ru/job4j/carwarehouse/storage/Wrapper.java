package ru.job4j.carwarehouse.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.function.Function;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class Wrapper {

    private static SessionFactory factory =  new Configuration()
            .configure()
            .buildSessionFactory();

    public static <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final org.hibernate.Transaction transaction = session.beginTransaction();
        try {
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}