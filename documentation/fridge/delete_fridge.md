**Delete House**
----
Delete a house

* **URL** `/house/<H_ID>/fridge/<ID>`
* **Method:** `DELETE`
*  **Authentication required:** YES
*  **Permission required:** YES

* **Data Params** NONE
* **Success Response:**
    * **Code:** 201 <br />
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
      **Content:** `Access Denied`

  OR

    * **Code:** 400 <br />
      **Content:** `The fridge does not exists`

  OR

    * **Code:** 400 <br />
      **Content:** `The house does not exists`
* **Sample Call:**
    ```shell
    curl --location --request DELETE 'https://localhost:8443/house/2/fridge' \
        --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
        --header 'API-KEY: VkNrYnpTRkl6Q3JIR25VTXp2cTlLWkZkSUxXZmlVbGk=' \
        --header 'Content-Type: application/json'
    ```