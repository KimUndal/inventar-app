package com.undal.inventarapp.shared.model;

import java.util.Objects;
import java.util.UUID;

public class Inventory {
    private String uuid;
    private String mobelId;
    private String tekniskUtyrId;
    private String utsmykningId;

    public Inventory(String mobelId, String tekniskUtyrId, String utsmykningId) {
        this.uuid = UUID.randomUUID().toString();
        this.mobelId = mobelId;
        this.tekniskUtyrId = tekniskUtyrId;
        this.utsmykningId = utsmykningId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobelId() {
        return mobelId;
    }

    public void setMobelId(String mobelId) {
        this.mobelId = mobelId;
    }

    public String getTekniskUtyrId() {
        return tekniskUtyrId;
    }

    public void setTekniskUtyrId(String tekniskUtyrId) {
        this.tekniskUtyrId = tekniskUtyrId;
    }

    public String getUtsmykningId() {
        return utsmykningId;
    }

    public void setUtsmykningId(String utsmykningId) {
        this.utsmykningId = utsmykningId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(uuid, inventory.uuid) && Objects.equals(mobelId, inventory.mobelId) && Objects.equals(tekniskUtyrId, inventory.tekniskUtyrId) && Objects.equals(utsmykningId, inventory.utsmykningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, mobelId, tekniskUtyrId, utsmykningId);
    }

    @Override
    public String toString() {
        return "Inventar{" +
                "uuid='" + uuid + '\'' +
                ", mobelId='" + mobelId + '\'' +
                ", tekniskUtyrId='" + tekniskUtyrId + '\'' +
                ", utsmykningId='" + utsmykningId + '\'' +
                '}';
    }
}
