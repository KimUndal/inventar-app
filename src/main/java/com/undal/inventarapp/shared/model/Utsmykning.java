package com.undal.inventarapp.shared.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Utsmykning extends Inventory{

    public Utsmykning(){}
    public Utsmykning(String inventoryType, String category, String description, LocalDate dateOfPurchase, int price, int numberOfPurchase, String placement) {
        super(inventoryType, category, description, dateOfPurchase, price, numberOfPurchase, placement);
    }

}
