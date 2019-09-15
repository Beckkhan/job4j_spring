package ru.job4j.chat.repository;

import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageRepo {

    private final static MessageRepo INSTANCE = new MessageRepo();

    private final AtomicInteger idCounter = new AtomicInteger();

    private final List<Message> messageList = new CopyOnWriteArrayList<>();

    private MessageRepo() {
        this.greeting();
    }

    public static MessageRepo getInstance() {
        return INSTANCE;
    }

    private void greeting() {
        User admin = new User("Admin","admin", "admin");
        Message message = new Message("Let`s Start", admin);
        this.add(message);
    }

    public Message add(Message message) {
        message.setId(String.valueOf(idCounter.incrementAndGet()));
        messageList.add(message);
        return message;
    }

    /**
     * This method returns information about all messages in the store.
     * @return messageList
     */
    public List<Message> findAll() {
        return this.messageList;
    }

    public void clearList() {
        this.messageList.clear();
    }
}