package ru.job4j.intro;

import org.springframework.stereotype.Component;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 02.08.2019
 */

@Component
public class CarDealerVisit implements Visit {

    private int questionNumber = 0;

    private String[] customerQuestions = {
            "What is the cost of the car?",
            "What conditions guarantee?",
            "What is the cost of service?",
            "What is the fuel consumption?"
    };

    @Override
    public String askQuestion() {
        if (questionNumber == 4) {
            questionNumber = 0;
        }
        return customerQuestions[questionNumber++];
    }
}