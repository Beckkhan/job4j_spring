package ru.job4j.intro;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.assertTrue;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 02.08.2019
 */
public class CarDealerTest {

    private ClassPathXmlApplicationContext context
            = new ClassPathXmlApplicationContext("dealer-spring-context.xml");
    private ClassPathXmlApplicationContext annContext
            = new ClassPathXmlApplicationContext("dealer-annotation-spring-context.xml");

    @After
    public void closeContext() {
        context.close();
        annContext.close();
    }

    @Test
    public void whenVisitSkodaDealer() {
        CarDealer skoda = context.getBean("skoda", CarDealer.class);
        assertTrue(skoda.getCustomerQuestion().equals("What is the cost of the car?"));
        assertTrue(skoda.answerQuestion().equals("30.000 USD"));
        assertTrue(skoda.getCustomerQuestion().equals("What conditions guarantee?"));
        assertTrue(skoda.answerQuestion().equals("3 years"));
        assertTrue(skoda.getCustomerQuestion().equals("What is the cost of service?"));
        assertTrue(skoda.answerQuestion().equals("Free for 2 years"));
        assertTrue(skoda.getCustomerQuestion().equals("What is the fuel consumption?"));
        assertTrue(skoda.answerQuestion().equals("10 litres in the combined cycle of city/highway"));
    }

    @Test
    public void whenVisitToyotaDealer() {
        CarDealer toyota = context.getBean("toyota", CarDealer.class);
        assertTrue(toyota.getCustomerQuestion().equals("What is the cost of the car?"));
        assertTrue(toyota.answerQuestion().equals("40.000 USD"));
        assertTrue(toyota.getCustomerQuestion().equals("What conditions guarantee?"));
        assertTrue(toyota.answerQuestion().equals("5 years"));
        assertTrue(toyota.getCustomerQuestion().equals("What is the cost of service?"));
        assertTrue(toyota.answerQuestion().equals("Free for 3 years"));
        assertTrue(toyota.getCustomerQuestion().equals("What is the fuel consumption?"));
        assertTrue(toyota.answerQuestion().equals("10 litres in the combined cycle of city/highway"));
    }

    @Test
    public void whenVisitMercedesDealer() {
        CarDealer mercedes = annContext.getBean(MercedesDealer.class);
        assertTrue(mercedes.getCustomerQuestion().equals("What is the cost of the car?"));
        assertTrue(mercedes.answerQuestion().equals("60.000 USD"));
        assertTrue(mercedes.getCustomerQuestion().equals("What conditions guarantee?"));
        assertTrue(mercedes.answerQuestion().equals("7 years"));
        assertTrue(mercedes.getCustomerQuestion().equals("What is the cost of service?"));
        assertTrue(mercedes.answerQuestion().equals("Free for 5 years"));
        assertTrue(mercedes.getCustomerQuestion().equals("What is the fuel consumption?"));
        assertTrue(mercedes.answerQuestion().equals("12 litres in the combined cycle of city/highway"));
    }
}