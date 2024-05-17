package com.undal.inventarapp.shared.model;

import java.util.Date;
import java.util.Objects;

public class Mobel extends Inventory{
    private int lifeExpectancy;

    public Mobel() {
    }

    public Mobel(String category, String description, Date dateOfPurchase, int price, int numberOfPurchase, String placement, int lifeExpectancy) {
        super(category, description, dateOfPurchase, price, numberOfPurchase, placement);
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }
}
