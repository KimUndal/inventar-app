package com.undal.inventarapp.shared.model;

import java.util.Objects;

public class InventoryStatus {
    private int statusId;
    private int inventoryId;
    private Status status;

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    private String statusDescription;
    private String dateChanged;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryStatus that = (InventoryStatus) o;
        return statusId == that.statusId && inventoryId == that.inventoryId && status == that.status && Objects.equals(statusDescription, that.statusDescription) && Objects.equals(dateChanged, that.dateChanged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, inventoryId, status, statusDescription, dateChanged);
    }

    @Override
    public String toString() {
        return "InventoryStatus{" +
                "statusId=" + statusId +
                ", inventoryId=" + inventoryId +
                ", status=" + status +
                ", statusDescription='" + statusDescription + '\'' +
                ", dateChanged='" + dateChanged + '\'' +
                '}';
    }
}
