package io.app;

import io.entity.House;

import java.util.HashMap;
import java.util.Map;

public class App {

    private static App INSTANCE_APP = null;
    private static int houseNumber = 0;
    private final Map<Integer, House> houses;

    private App() {
        this.houses = new HashMap<>();
    }

    public static App getApp() {
        if (INSTANCE_APP == null) {
            INSTANCE_APP = new App();
        }
        return INSTANCE_APP;
    }

    public static int getNextId() {
        houseNumber++;
        return houseNumber;
    }

    public Map<Integer, House> getHouses() {
        return houses;
    }

    public boolean hasHouse(Integer id) {
        return houses.containsKey(id);
    }

}
