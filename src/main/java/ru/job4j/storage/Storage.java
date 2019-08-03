package ru.job4j.storage;

import java.util.List;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 03.08.2019
 */
public interface Storage {

    int addUser(User user);

    User getUser(int id);

    List<User> getAll();
}