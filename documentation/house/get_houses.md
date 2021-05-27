**Get Houses**
----
Get all the houses

* **URL** `/house`
* **Method:** `GET`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params** NONE
* **Success Response:**
    * **Code:** 200 <br />
      **Content:** 
      ```json
      [
          { 
              "<ID>": {
                "address": "<ADDRESS>",
                "zip": "<ZIP>"
              }
          }
      ]
      ```

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`
* **Sample Call:**
    ```shell
    curl --location --request GET 'https://localhost:8443/house' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA=='
    ```