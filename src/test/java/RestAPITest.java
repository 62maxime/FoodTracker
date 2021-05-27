import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.ApiKey;
import entity.Id;
import io.entity.Food;
import io.entity.Fridge;
import io.entity.House;
import io.entity.SensorState;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestAPITest {

    public static String houseId = "NA";
    public static String apiKey = "NA";
    public final static String basicAuth = "ZGVmYXVsdDpwYXNzd29yZA==";

    public static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().
                    setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).
                    setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException e) {
            System.err.println("KeyManagementException in creating http client instance");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException in creating http client instance");
        } catch (KeyStoreException e) {
            System.err.println("KeyStoreException in creating http client instance");
        }
        return httpClient;
    }

    @Test
    @Order(1)
    @DisplayName("Creation of a new house")
    public void createNewHouse()
            throws IOException, ClassNotFoundException {

        // Given
        HttpPost post = new HttpPost("https://localhost:8443/house");
        String json = "{\n" +
                "    \"address\": \"Defense\",\n" +
                "    \"zip\": \"FR\"\n" +
                "}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(post);

        // check that the house is created
        assertEquals(201, res.getStatusLine().getStatusCode(), "The creation has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        Id id;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            id = gson.fromJson(streamReader, Id.class);
            assertNotEquals("NA", id.getId());
        }

        houseId = id.getId();
    }

    @Test
    @Order(2)
    @DisplayName("Retrieval of the house")
    public void retrieveNewHouse()
            throws IOException, ClassNotFoundException {

        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The creation has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        House house;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            house = gson.fromJson(streamReader, House.class);
            assertNotEquals("NA", house.getId());
            assertEquals("Defense", house.getAddress());
            assertEquals("FR", house.getZip());
        }
    }


    @Test
    @Order(2)
    @DisplayName("Creation of a fridge")
    public void createFridge()
            throws IOException, ClassNotFoundException {

        // Given
        HttpPost post = new HttpPost("https://localhost:8443/house/" + houseId + "/fridge");
        String json = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"state\": \"RUNNING\",\n" +
                "    \"maxVolume\": 11\n" +
                "}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(post);

        // check that the house is created
        assertEquals(201, res.getStatusLine().getStatusCode(), "The creation has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        ApiKey apiKey;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            apiKey = gson.fromJson(streamReader, ApiKey.class);
            assertNotEquals("NA", apiKey.getApiKey());
        }

        RestAPITest.apiKey = apiKey.getApiKey();
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve the fridge")
    public void retrieveFridge()
            throws IOException, ClassNotFoundException {

        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId + "/fridge/1");
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("API-KEY", apiKey);
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The retrieval has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        Fridge fridge;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            fridge = gson.fromJson(streamReader, Fridge.class);
            assertEquals("1", fridge.getId());
            assertEquals(11, fridge.getMaxVolume());
            assertEquals(SensorState.RUNNING, fridge.getState());
        }

    }

    @Test
    @Order(3)
    @DisplayName("Creation of a food")
    public void createFood()
            throws IOException, ClassNotFoundException {

        // Given
        HttpPost post = new HttpPost("https://localhost:8443/house/" + houseId + "/fridge/1/food");
        String json = "{\n" +
                "    \"name\": \"tomato\",\n" +
                "    \"volume\": 2,\n" +
                "    \"expireDate\": \"2021-05-30\"\n" +
                "}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("API-KEY", apiKey);
        post.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(post);

        // check that the house is created
        assertEquals(201, res.getStatusLine().getStatusCode(), "The creation has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        Food food;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            food = gson.fromJson(streamReader, Food.class);
            assertEquals("tomato", food.getName());
            assertEquals(2, food.getVolume());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(food.getExpireDate());
            assertEquals("2021-05-30", strDate);
        }

    }

    @Test
    @Order(4)
    @DisplayName("Retrieval of food")
    public void retrieveFood()
            throws IOException, ClassNotFoundException {

        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId + "/fridge/1/food/tomato");
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("API-KEY", apiKey);
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The retrieval has failed");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        // Check that we've received the ID
        Food food;
        try (InputStreamReader streamReader = new InputStreamReader(res.getEntity().getContent())) {
            food = gson.fromJson(streamReader, Food.class);
            assertEquals("tomato", food.getName());
            assertEquals(2, food.getVolume());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(food.getExpireDate());
            assertEquals("2021-05-30", strDate);
        }

    }

    @Test
    @Order(5)
    @DisplayName("Exceed fridge volume")
    public void exceedFridgeSize()
            throws IOException, ClassNotFoundException {
        for (int i = 0; i < 10; i++) {
            // Given
            HttpPost post = new HttpPost("https://localhost:8443/house/" + houseId + "/fridge/1/food");
            String json = "{\n" +
                    "    \"name\": \"tomato" + i +"\",\n" +
                    "    \"volume\": 1,\n" +
                    "    \"expireDate\": \"2021-05-30\"\n" +
                    "}";
            StringEntity entity = new StringEntity(json);
            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            post.setHeader("API-KEY", apiKey);
            post.setHeader("Authorization", "Basic " + basicAuth);

            //Get http client
            CloseableHttpClient httpClient = getCloseableHttpClient();

            //Execute HTTP method
            CloseableHttpResponse res = httpClient.execute(post);

            // check that the house is created
            if (i == 9) {
                assertEquals(400, res.getStatusLine().getStatusCode(), "The check of the max volume is not done");

            } else {
                assertEquals(201, res.getStatusLine().getStatusCode(), "The creation has failed");
            }
        }

    }

    @Test
    @Order(6)
    @DisplayName("Deletion of Food")
    public void deleteFood()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId + "/fridge/1/food/tomato");
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("API-KEY", apiKey);
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(7)
    @DisplayName("Deletion of Fridge")
    public void deleteFridge()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId + "/fridge/1");
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("API-KEY", apiKey);
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(8)
    @DisplayName("Deletion of House")
    public void deleteHouse()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId);
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(200, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(9)
    @DisplayName("Retrieval of unknown House")
    public void retrieveUnknownHouse()
            throws IOException, ClassNotFoundException {
        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(8)
    @DisplayName("Retrieval of unknown Fridge")
    public void retrieveUnknownFridge()
            throws IOException, ClassNotFoundException {
        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId+ "/fridge/1");
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(7)
    @DisplayName("Retrieval of unknown Food")
    public void retrieveUnknownFood()
            throws IOException, ClassNotFoundException {
        // Given
        HttpGet get = new HttpGet("https://localhost:8443/house/" + houseId+ "/fridge/1/food/1");
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(get);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The creation has failed");
    }

    @Test
    @Order(7)
    @DisplayName("Deletion of unknown Food")
    public void deleteUnknownFood()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId+ "/fridge/1/food/tomato");
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The creation has failed");
    }


    @Test
    @Order(8)
    @DisplayName("Deletion of unknown Fridge")
    public void deleteUnknownFridge()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId+ "/fridge/1");
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The deletion has failed");
    }

    @Test
    @Order(9)
    @DisplayName("Deletion of unknown House")
    public void deleteUnknownHouse()
            throws IOException, ClassNotFoundException {
        // Given
        HttpDelete delete = new HttpDelete("https://localhost:8443/house/" + houseId);
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-type", "application/json");
        delete.setHeader("Authorization", "Basic " + basicAuth);

        //Get http client
        CloseableHttpClient httpClient = getCloseableHttpClient();

        //Execute HTTP method
        CloseableHttpResponse res = httpClient.execute(delete);

        // check that the house is created
        assertEquals(400, res.getStatusLine().getStatusCode(), "The deletion has failed");
    }



}
