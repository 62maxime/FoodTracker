**Get Fridge**
----
Get Fridges by ID

* **URL** `/house/<H_ID>/fridge/<ID>`
* **Method:** `GET`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params** NONE
* **Success Response:**
    * **Code:** 200 <br />
      **Content:** 
      ```json
      {
          "id": "<ID>",
          "state": "<STATE>",
          "maxVolume": "<MAX_VOLUME>"
      }
      ```

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`
      
  OR

    * **Code:** 400 <br />
      **Content:** `The fridge does not exists`

* **Sample Call:**
    ```shell
    curl --location --request GET 'https://localhost:8443/house/2/fridge/2' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA=='
    ```