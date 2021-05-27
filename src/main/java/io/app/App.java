package io.app;

import io.entity.House;

import java.security.SecureRandom;
import java.util.Base64;
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

    public House getHouse(Integer id) {
        return houses.get(id);
    }

    public House removeHouse(Integer houseId) {
        return houses.remove(houseId);
    }

    public boolean hasHouse(House house) {
        return houses.containsValue(house);
    }

    public String generateApiKey() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }


}
