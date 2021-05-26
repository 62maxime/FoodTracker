package io.entity;

import java.io.Serializable;
import java.util.*;

public class Fridge implements Serializable {

    private String id;
    private SensorState state;
    private int maxVolume;
    private List<Food> food;

    public Fridge() {
        this.id = "";
        this.state = SensorState.UNKNOWN;
        this.maxVolume = -1;
        this.food = new ArrayList<>();
    }

    public Fridge(String id, SensorState state, int maxVolume) {
        this.id = id;
        this.state = state;
        this.maxVolume = maxVolume;
        this.food = new ArrayList<>();
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public SensorState getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public boolean hasFood(String name) {
        return food.stream().filter((f) -> f.getName().equals(name)).findAny().isPresent();
    }

    public List<Food> getFood() {
        return food;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", maxVolume=" + maxVolume +
                ", food=" + food +
                '}';
    }
}