package com.undal.inventarapp.shared.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Inventory {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty inventoryType = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateOfPurchase = new SimpleObjectProperty<>();
    private final IntegerProperty price = new SimpleIntegerProperty();
    private final IntegerProperty numberOfPurchase = new SimpleIntegerProperty();
    private final StringProperty placement = new SimpleStringProperty();

    public Inventory() {
    }

    public Inventory(String inventoryType, String category, String description, LocalDate dateOfPurchase, int price, int numberOfPurchase, String placement) {
        setInventoryType(inventoryType);
        setCategory(category);
        setDescription(description);
        setDateOfPurchase(dateOfPurchase);
        setPrice(price);
        setNumberOfPurchase(numberOfPurchase);
        setPlacement(placement);
    }

    public StringProperty inventoryTypeProperty() {
        return inventoryType;
    }

    public String getInventoryType() {
        return inventoryType.get();
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType.set(inventoryType);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObjectProperty<LocalDate> dateOfPurchaseProperty() {
        return dateOfPurchase;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase.get();
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase.set(dateOfPurchase);
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public IntegerProperty numberOfPurchaseProperty() {
        return numberOfPurchase;
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase.get();
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase.set(numberOfPurchase);
    }

    public StringProperty placementProperty() {
        return placement;
    }

    public String getPlacement() {
        return placement.get();
    }

    public void setPlacement(String placement) {
        this.placement.set(placement);
    }

    // Equals, hashCode, and other methods...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) && Objects.equals(inventoryType, inventory.inventoryType) && Objects.equals(category, inventory.category) && Objects.equals(description, inventory.description) && Objects.equals(dateOfPurchase, inventory.dateOfPurchase) && Objects.equals(price, inventory.price) && Objects.equals(numberOfPurchase, inventory.numberOfPurchase) && Objects.equals(placement, inventory.placement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventoryType, category, description, dateOfPurchase, price, numberOfPurchase, placement);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", inventoryType='" + inventoryType + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", price=" + price +
                ", numberOfPurchase=" + numberOfPurchase +
                ", placement='" + placement + '\'' +
                '}';
    }
}
