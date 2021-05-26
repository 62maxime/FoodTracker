# FoodTracker

## API description
|Path|Method|Description|
|---|---|---|
|/house                                |POST |  Create new house
|/house                                |GET |  Retrieve all the house
|/house/H_ID                          |GET |  Retrieve the house HID
|/house/H_ID                          |DELETE |  Delete the house
|/house/H_ID/fridge                   |POST |  Create a new fridge in the house HID
|/house/H_ID/fridge                   |GET |  Retrieve all the fridges in the house HID
|/house/H_ID/fridge/FR_ID             |GET |  Retrieve the fridge  FID in the house HID
|/house/H_ID/fridge/FR_ID/food        |POST |  Create a new food in the fridge id
|/house/H_ID/fridge/FR_ID/food        |GET |  Retrieve all the foods in the fridge id
|/house/H_ID/fridge/FR_ID/food/FO_ID |GET |  Retrieve the food FFID in the fridge id
|/house/H_ID/fridge/FR_ID/food/FO_ID |DELETE |  Delete food data

## Run
```
mvn clean install jetty:run
```