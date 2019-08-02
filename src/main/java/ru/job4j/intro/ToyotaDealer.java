package ru.job4j.intro;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 02.08.2019
 */
public class ToyotaDealer implements CarDealer {

    private Visit customer;

    private int answerNumber = 0;

    private String[] answers = {
            "40.000 USD",
            "5 years",
            "Free for 3 years",
            "10 litres in the combined cycle of city/highway"
    };

    public void setCustomer(Visit customer) {
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