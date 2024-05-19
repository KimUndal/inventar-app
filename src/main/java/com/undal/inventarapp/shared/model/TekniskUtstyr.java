package com.undal.inventarapp.shared.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class TekniskUtstyr extends Inventory{

    public TekniskUtstyr(){}
    public TekniskUtstyr(String inventoryType, String category, String description, LocalDate dateOfPurchase, int price, int numberOfPurchase, String placement, String category1, String description1, Date dateOfPurchase1, int price1, int numberOfPurchase1, String placement1) {
        super(inventoryType, category, description, dateOfPurchase, price, numberOfPurchase, placement);
    }

}
