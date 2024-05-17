package com.undal.inventarapp.shared.model;

import java.util.Date;
import java.util.Objects;
public abstract class Inventory {
    private int id;
    private String category;
    private String description;
    private Date dateOfPurchase;
    private int price;
    private int numberOfPurchase;
    private String placement;

    public Inventory() {
    }

    public Inventory(String category, String description, Date dateOfPurchase, int price,int numberOfPurchase, String placement) {
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
        Inventory inventory = (Inventory) o;
        return id == inventory.id && price == inventory.price  && numberOfPurchase == inventory.numberOfPurchase && Objects.equals(category, inventory.category) && Objects.equals(description, inventory.description) && Objects.equals(dateOfPurchase, inventory.dateOfPurchase) && Objects.equals(placement, inventory.placement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, description, dateOfPurchase, price, numberOfPurchase, placement);
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
                "id=" + id +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", price=" + price +
                ", numberOfPurchase=" + numberOfPurchase +
                ", placement='" + placement + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
