package com.undal.inventarapp.shared.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Mobel extends Inventory{
    private int lifeExpectancy;

    public Mobel() {
    }

    public Mobel(String inventoryType, String category, String description, LocalDate dateOfPurchase, int price, int numberOfPurchase, String placement, int lifeExpectancy) {
        super(inventoryType, category, description, dateOfPurchase, price, numberOfPurchase, placement);
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", lifeExpectancy='" + lifeExpectancy + '\'' +
                '}';
    }
}
