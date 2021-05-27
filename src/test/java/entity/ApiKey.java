package entity;

import com.google.gson.annotations.Expose;

public class ApiKey {
    @Expose
    private final String apiKey;

    public ApiKey() {
        apiKey = "NA";
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
