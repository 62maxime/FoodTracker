package io.entity;

import java.io.Serializable;
import java.util.*;

public class House implements Serializable {

    private int id;
    private String address;
    private String zip;
    private List<Fridge> fridges;

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
}
