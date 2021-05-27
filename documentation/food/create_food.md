**Create Food**
----
Create food in a fridge according to the provided json

* **URL** `/house/<H_ID>/fridge/<F_ID>/food`
* **Method:** `POST`
*  **Authentication required:** YES
*  **Permission required:** YES

* **Data Params**
    * name  `str` : name of the food
    * volume  `int` : volume of the food
    * expireDate  `str` : expiration date with the format "YYYY-MM-DD"
* **Success Response:**
  Return the apiKey of the fridge
    * **Code:** 201 <br />
      **Content:** 
      ```json
      {
          "name": "<NAME>",
          "volume": "<VOLUME>",
          "expireDate": "<DATE>"
      }
      ```

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`

  OR

    * **Code:** 400 <br />
      **Content:** `The house does not exists`

  OR

    * **Code:** 400 <br />
      **Content:** `The fridge does not exists`   

  OR

    * **Code:** 400 <br />
      **Content:** `Food with the same name exists`
* **Sample Call:**
    ```shell
    curl --location --request POST 'https://localhost:8443/house/2/fridge/1/food' \
    --header 'API-KEY: VkNrYnpTRkl6Q3JIR25VTXp2cTlLWkZkSUxXZmlVbGk=' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "tomato",
        "volume": 11,
        "expireDate": "1999-09-12"
    }'
    ```
* **Notes:**
Food must have different names