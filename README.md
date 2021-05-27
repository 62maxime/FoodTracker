# FoodTracker

## TODO List
- [x] Basic Rest API
- [x] Restrict access of fridges
- [x] Secure communication
- [x] Basic Authentication to restrict secure the REST API
- [ ] Persistent Storage (BDD or File)
- [ ] User Management

## API description
|Path|Method|Description|Status|
|---|---|---|---|
|/house                                |POST |  Create new house | ✓
|/house                                |GET |  Retrieve all the house | ✓
|/house/H_ID                          |GET |  Retrieve the house HID | ✓
|/house/H_ID                          |DELETE |  Delete the house | ✓
|/house/H_ID/fridge                   |POST |  Create a new fridge in the house HID | ✓
|/house/H_ID/fridge                   |GET |  Retrieve all the fridges in the house HID | ✓
|/house/H_ID/fridge/FR_ID             |GET |  Retrieve the fridge  FID in the house HID | ✓
|/house/H_ID/fridge/FR_ID             |PUT |  Update info of the fridge  FID in the house HID | 
|/house/H_ID/fridge/FR_ID/food        |POST |  Create a new food in the fridge id | ✓
|/house/H_ID/fridge/FR_ID/food        |GET |  Retrieve all the foods in the fridge id | ✓
|/house/H_ID/fridge/FR_ID/food/FO_ID |GET |  Retrieve the food FFID in the fridge id | ✓
|/house/H_ID/fridge/FR_ID/food/FO_ID |DELETE |  Delete food data | ✓

## Installation
1. Build the docker image
    ```
    docker build -t  foodtracker .
    ```

## Run
1. Create keystore for SSL as **<CONFIG_PATH>/jetty.keystore** (The password must be **secret**)
    ```
    keytool -genkey -alias jetty -keyalg RSA -keystore src/main/resources/jetty.keystore -storepass secret -keypass secret -dname "CN=localhost"
    ```
2. Create a file with basic authentication in **<CONFIG_PATH>/credentials**
    ```
    default:password
    ```
3. Start docker image
    ```
    docker run --rm  -p 8443:8443 --volume <CONFIG_PATH>:/var/foodTracker/config --name foodtracker foodtracker:latest
    ```