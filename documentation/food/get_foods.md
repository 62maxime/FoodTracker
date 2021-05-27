**Get all food**
----
Get all food in the fridge 

* **URL** `/house/<H_ID>/fridge/<F_ID>/food`
* **Method:** `GET`
*  **Authentication required:** YES
*  **Permission required:** YES

* **Data Params** NONE
* **Success Response:**
    * **Code:** 200 <br />
      **Content:**
      ```json
      [
          {
              "name": "<NAME>",
              "volume": "<VOLUME>",
              "expireDate": "<DATE>"
          }
      ]
      ```

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`
      OR

    * **Code:** 400 <br />
      **Content:** `The fridge does not exists`

  OR

    * **Code:** 400 <br />
      **Content:** `The house does not exists`

* **Sample Call:**
    ```shell
    curl --location --request GET 'https://localhost:8443/house/2/fridge/1/food' \
        --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
        --header 'API-KEY: VkNrYnpTRkl6Q3JIR25VTXp2cTlLWkZkSUxXZmlVbGk='
    ```