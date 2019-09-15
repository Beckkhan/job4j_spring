package ru.job4j.chat.domain;

import java.util.Objects;

public class Message {

    private String id;
    private String text;
    private User user;

    public Message() {
    }

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(text, message.text)
                && Objects.equals(user, message.user);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", user.getName(), text);
    }
}