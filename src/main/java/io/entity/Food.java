package io.entity;

import java.io.Serializable;
import java.util.Date;

public class Food implements Serializable {

    private String name;
    private int volume;
    private Date expireDate;

    public Food() {
        this.name = "NA";
        this.volume = -1;
        this.expireDate = null;
    }

    public Food(String name, int volume, Date expireDate) {
        this.name = name;
        this.volume = volume;
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public int getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                ", expireDate=" + expireDate +
                '}';
    }
}
