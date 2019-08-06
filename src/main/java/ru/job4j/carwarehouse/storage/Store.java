package ru.job4j.carwarehouse.storage;

import java.util.List;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public interface Store<T> {

    int add(T entity);

    int update(T entity);

    int delete(int id);

    List<T> getAll();

    T getById(int id);

    T getByName(String name);
}