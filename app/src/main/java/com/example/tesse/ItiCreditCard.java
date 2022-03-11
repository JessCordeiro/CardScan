package com.example.tesse;

public class ItiCreditCard {
    private int Number;
    private String Name;

    public ItiCreditCard(int N, String E) {
        Number = N;
        Name = E;

    }

    public int getNumber() {
        return Number;
    }

    public String getName() {
        return Name;
    }
}
