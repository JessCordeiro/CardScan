package com.example.tesse;

public class ThisCreditCard {
    private int Number;
    private String Name;

    public ThisCreditCard(int N, String E) {
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
