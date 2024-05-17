package com.undal.inventarapp.shared.model;

import java.util.Date;
import java.util.Objects;

public class Utsmykning extends Inventory{

    public Utsmykning(){}
    public Utsmykning(String category, String description, Date dateOfPurchase, int price, int numberOfPurchase, String placement) {
        super(category, description, dateOfPurchase, price, numberOfPurchase, placement);
    }
}
