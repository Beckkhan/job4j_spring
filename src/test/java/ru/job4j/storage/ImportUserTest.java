package ru.job4j.storage;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.assertEquals;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 03.08.2019
 */

public class ImportUserTest {

    private ClassPathXmlApplicationContext context
            = new ClassPathXmlApplicationContext("storage-spring-context.xml");

    private ImportUser importUser = context.getBean("importUser", ImportUser.class);

    @After
    public void closeContext() {
        context.close();
    }

    @Test
    public void whenAddUser() {
        User first = new User(1, "First Name", "First Lastname");
        importUser.addUser(first);
        assertEquals(importUser.getUser(1), first);
    }

    @Test
    public void whenGetAllUsers() {
        User second = new User(2, "Second Name", "Second Lastname");
        User third = new User(3, "Third Name", "Third Lastname");
        importUser.addUser(second);
        importUser.addUser(third);
        assertEquals(importUser.getAll().size(), 2);
    }
}