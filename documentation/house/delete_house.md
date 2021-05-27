**Delete House**
----
Delete a house

* **URL** `/house/<ID>`
* **Method:** `DELETE`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params** NONE
* **Success Response:**
    * **Code:** 201 <br />
      **Content:**
      ```json
      {
        "address": "<ADDRESS>",
        "zip": "<ZIP>"
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
      **Content:** `The house is not empty`
* **Sample Call:**
    ```shell
    curl --location --request DELETE 'https://localhost:8443/house/1' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
    --header 'Content-Type: application/json'
    ```
* **Notes:**
Houses must be empty in order to be removed