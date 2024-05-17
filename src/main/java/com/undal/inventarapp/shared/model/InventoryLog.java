package com.undal.inventarapp.shared.model;

import java.util.Objects;

public class InventoryLog {
    private String id;
    private int mobel;
    private int tekniskUtstyr;
    private int utsmykning;

    public InventoryLog(int utsmykning, int tekniskUtstyr, int mobel) {
        this.utsmykning = utsmykning;
        this.tekniskUtstyr = tekniskUtstyr;
        this.mobel = mobel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMobel() {
        return mobel;
    }

    public void setMobel(int mobel) {
        this.mobel = mobel;
    }

    public int getTekniskUtstyr() {
        return tekniskUtstyr;
    }

    public void setTekniskUtstyr(int tekniskUtstyr) {
        this.tekniskUtstyr = tekniskUtstyr;
    }

    public int getUtsmykning() {
        return utsmykning;
    }

    public void setUtsmykning(int utsmykning) {
        this.utsmykning = utsmykning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryLog that = (InventoryLog) o;
        return mobel == that.mobel && tekniskUtstyr == that.tekniskUtstyr && utsmykning == that.utsmykning && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mobel, tekniskUtstyr, utsmykning);
    }

    @Override
    public String toString() {
        return "InventarLog{" +
                "id='" + id + '\'' +
                ", mobel=" + mobel +
                ", tekniskUtstyr=" + tekniskUtstyr +
                ", utsmykning=" + utsmykning +
                '}';
    }
}
