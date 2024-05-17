package com.undal.inventarapp.shared.model;

import java.util.Date;
import java.util.Objects;

public class Mobel {
    private String category;
    private String description;
    private Date dateOfPurchase;
    private int price;
    private int lifeExpectancy;
    private int numberOfPurchase;
    private String placement;

    public Mobel(String category, String description, Date dateOfPurchase, int price, int lifeExpectancy, int numberOfPurchase, String placement) {
        this.category = category;
        this.description = description;
        this.dateOfPurchase = dateOfPurchase;
        this.price = price;
        this.lifeExpectancy = lifeExpectancy;
        this.numberOfPurchase = numberOfPurchase;
        this.placement = placement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mobel mobel = (Mobel) o;
        return price == mobel.price && lifeExpectancy == mobel.lifeExpectancy && Objects.equals(category, mobel.category) && Objects.equals(description, mobel.description) && Objects.equals(dateOfPurchase, mobel.dateOfPurchase) && Objects.equals(placement, mobel.placement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, description, dateOfPurchase, price, lifeExpectancy, placement);
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase = numberOfPurchase;
    }

    @Override
    public String toString() {
        return "Mobel{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", price=" + price +
                ", lifeExpectancy=" + lifeExpectancy +
                ", numberOfPurchase=" + numberOfPurchase +
                ", placement='" + placement + '\'' +
                '}';
    }
}
