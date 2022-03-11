package com.example.tesse;

public class Card {
    private String Name;
    private int Number;

    public Card(int N,  String E) {
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
