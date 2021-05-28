package io.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fridge implements Serializable {

    @Expose
    private final String id;
    @Expose
    private final SensorState state;
    @Expose
    private final int maxVolume;
    private final List<Food> food;
    private String apiKey;

    public Fridge() {
        this.id = null;
        this.state = null;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
