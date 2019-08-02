package ru.job4j.intro;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 02.08.2019
 */

@Component
public class MercedesDealer implements CarDealer {

    private Visit customer;

    private int answerNumber = 0;

    private String[] answers = {
            "60.000 USD",
            "7 years",
            "Free for 5 years",
            "12 litres in the combined cycle of city/highway"
    };

    @Autowired
    public MercedesDealer(Visit customer) {
        this.customer = customer;
    }

    @Override
    public String getCustomerQuestion() {
        return customer.askQuestion();
    }

    @Override
    public String answerQuestion() {
        if (answerNumber == 4) {
            answerNumber = 0;
        }
        return answers[answerNumber++];
    }
}