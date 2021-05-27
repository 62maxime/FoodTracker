**Get House**
----
Get House by ID

* **URL** `/house/<ID>`
* **Method:** `GET`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params** NONE
* **Success Response:**
    * **Code:** 200 <br />
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
      **Content:** `A house does not exists`

* **Sample Call:**
    ```shell
    curl --location --request GET 'https://localhost:8443/house/1' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA=='
    ```