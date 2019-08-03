package ru.job4j.storage;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 03.08.2019
 */

@Component
public class MemoryStorage implements Storage {

    private Map<Integer, User> store = new HashMap<>();

    @Override
    public int addUser(User user) {
        store.putIfAbsent(user.getId(), user);
        return user.getId();
    }

    @Override
    public User getUser(int id) {
        return store.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(store.values());
    }
}