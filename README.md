# FoodTracker

## TODO List
- [x] Basic Rest API
- [x] Restrict access of fridges
- [x] Secure communication
- [x] Basic Authentication to restrict secure the REST API
- [ ] Persistent Storage (BDD or File)
- [ ] User Management
- [ ] Return code standard

## API description
### Basic Authentication
Call to methods with **Authentication required** set to YES must include in the header the username and  the password split by `:` encoded in base64.

Example:
```
Authorization: Basic bG9sOnNlY3VyZQ==
```

### API Key
Call to methods with **Permission required** set to YES must include in the header API-KEY to be able to modify some object.

Example:
```
API-KEY: VkNrYnpTRkl6Q3JIR25VTXp2cTlLWkZkSUxXZmlVbGk=
```

### Methods

|Path|Method|Description|Status
|---|---|---|---|
|[/house](documentation/house/create_house.md)|POST |  Create new house | ✓
|[/house](documentation/house/get_houses.md)|GET |  Retrieve all the house | ✓
|[/house/H_ID](documentation/house/get_house.md)|GET |  Retrieve the house HID | ✓
|[/house/H_ID](documentation/house/delete_house.md)|DELETE |  Delete the house | ✓ 
|[/house/H_ID/fridge](documentation/fridge/create_fridge.md)|POST |  Create a new fridge in the house HID | ✓
|[/house/H_ID/fridge](documentation/fridge/get_fridges.md)|GET |  Retrieve all the fridges in the house HID | ✓ 
|[/house/H_ID/fridge/FR_ID](documentation/fridge/get_fridge.md)|GET |  Retrieve the fridge  FID in the house HID | ✓
|[/house/H_ID/fridge/FR_ID](documentation/fridge/delete_fridge.md)|DELETE |Delete the fridge | ✓
|[/house/H_ID/fridge/FR_ID](documentation/fridge/update_fridge.md)|PUT |  Update info of the fridge  FID in the house HID |
|[/house/H_ID/fridge/FR_ID/food](documentation/food/create_food.md)|POST |  Create a new food in the fridge id | ✓ 
|[/house/H_ID/fridge/FR_ID/food](documentation/food/get_foods.md)|GET |  Retrieve all the foods in the fridge id | ✓ 
|[/house/H_ID/fridge/FR_ID/food/FO_ID](documentation/food/get_food.md)|GET |  Retrieve the food FFID in the fridge id | ✓ 
|[/house/H_ID/fridge/FR_ID/food/FO_ID](documentation/food/delete_food.md)|DELETE |  Delete food data | ✓ 

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