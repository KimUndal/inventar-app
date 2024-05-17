package com.undal.inventarapp.shared.model;

import java.util.Date;
import java.util.Objects;

public class TekniskUtstyr {
    private String category;
    private String description;
    private Date dateOfPurchase;
    private int price;
    private int numberOfPurchase;
    private String placement;

    public TekniskUtstyr(String category, String description, Date dateOfPurchase, int price, int numberOfPurchase, String placement) {
        this.category = category;
        this.description = description;
        this.dateOfPurchase = dateOfPurchase;
        this.price = price;
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
        TekniskUtstyr that = (TekniskUtstyr) o;
        return price == that.price && Objects.equals(category, that.category) && Objects.equals(description, that.description) && Objects.equals(dateOfPurchase, that.dateOfPurchase) && Objects.equals(placement, that.placement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, description, dateOfPurchase, price, placement);
    }

    @Override
    public String toString() {
        return "TekniskUtstyr{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", price=" + price +
                ", numberOfPurchase=" + numberOfPurchase +
                ", placement='" + placement + '\'' +
                '}';
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase = numberOfPurchase;
    }
}
