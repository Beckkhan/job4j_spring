package ru.job4j.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 03.08.2019
 */

@Component
public class ImportUser {

    private Storage storage;

    @Autowired
    public ImportUser(@Qualifier("memoryStorage") Storage storage) {
        this.storage = storage;
    }

    public void addUser(User user) {
        this.storage.addUser(user);
    }

    public User getUser(int id) {
        return this.storage.getUser(id);
    }

    public List<User> getAll() {
        return this.storage.getAll();
    }
}