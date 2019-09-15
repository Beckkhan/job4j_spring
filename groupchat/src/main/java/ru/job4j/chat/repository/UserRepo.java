package ru.job4j.chat.repository;

import ru.job4j.chat.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepo {

    private final static UserRepo INSTANCE = new UserRepo();

    private final AtomicInteger idCounter = new AtomicInteger();

    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    private UserRepo() {
        this.fillUsers();
    }

    public static UserRepo getInstance() {
        return INSTANCE;
    }

    private void fillUsers() {
        User admin = new User("Admin","admin", "admin");
        User first = new User("First", "first", "first");
        User second = new User("Second", "second", "second");
        this.add(admin);
        this.add(first);
        this.add(second);
    }

    public User add(User user) {
        user.setId(String.valueOf(idCounter.incrementAndGet()));
        users.put(user.getId(), user);
        return user;
    }

    /**
     * This method searches for the user by ID number.
     * @param user
     * @return user found
     */
    public User findById(User user) {
        return users.get(user.getId());
    }

    public User findByLogin(String login) {
        User result = null;
        for (User next : this.findAll()) {
            if (next.getLogin().equals(login)) {
                result = next;
            }
        }
        return result;
    }

    /**
     * This method returns information about all users in the store.
     * @return store
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}