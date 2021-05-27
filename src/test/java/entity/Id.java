package entity;

import com.google.gson.annotations.Expose;

public class Id {
    @Expose
    private final String id;

    public Id() {
        id = "NA";
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id='" + id + '\'' +
                '}';
    }
}
