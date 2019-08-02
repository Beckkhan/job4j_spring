package ru.job4j.intro;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 02.08.2019
 */
public class SkodaDealer implements CarDealer {

    private Visit customer;

    private int answerNumber = 0;

    private String[] answers = {
            "30.000 USD",
            "3 years",
            "Free for 2 years",
            "10 litres in the combined cycle of city/highway"
    };

    public SkodaDealer(Visit customer) {
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