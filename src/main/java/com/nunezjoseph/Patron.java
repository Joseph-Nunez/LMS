package com.nunezjoseph;

public class Patron {
    private String name;
    private int ID;
    private String address;
    private double owedAmount;

    public Patron(int ID, String name, String address, double owedAmount){
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.owedAmount = owedAmount;

    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public double getOwedAmount(){
        return owedAmount;
    }
}
