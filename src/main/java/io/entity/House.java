package io.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class House implements Serializable {

    private int id;
    @Expose
    private final String address;
    @Expose
    private final String zip;
    private final List<Fridge> fridges;

    public House(String address, String zip) {
        this.address = address;
        this.zip = zip;
        fridges = new ArrayList<>();
    }

    public House() {
        this.address = "";
        this.zip = "";
        fridges = new ArrayList<>();
    }

    public List<Fridge> getFridges() {
        return fridges;
    }

    public boolean hasFridge(String id) {
        return fridges.stream().filter((f) -> f.getId().equals(id)).findAny().isPresent();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "House{" +
                "address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", fridges=" + fridges +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return address.equals(house.address) && zip.equals(house.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, zip);
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public int getId() {
        return id;
    }
}
